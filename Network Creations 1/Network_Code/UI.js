const MIN_NOTE = 48;
const MAX_NOTE = 84;
const DEFAULT_BPM = 120;
const MAX_MIDI_BPM = 240;
const TEMPO_MIDI_CONTROLLER = 20; // Control changes for tempo for this controller id

let Tone = mm.Player.tone;

let temperature = 1.1;
let patternLength = 8;

let masterGain = new Tone.Gain(0.6).toMaster();
let reverb = new Tone.Convolver(
  'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/hm2_000_ortf_48k.mp3'
).connect(masterGain);
reverb.wet.value = 0.1;
let echo = new Tone.FeedbackDelay('8n.', 0.4).connect(
  new Tone.Gain(0.5).connect(reverb)
);
let lowPass = new Tone.Filter(5000).connect(echo).connect(reverb);
new Tone.LFO('8m', 3000, 5000).connect(lowPass.frequency).start();
let sampler = new Tone.Sampler({
  C3: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-c3.wav',
  'D#3': 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-ds3.wav',
  'F#3': 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-fs3.wav',
  A3: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-a3.wav',
  C4: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-c4.wav',
  'D#4': 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-ds4.wav',
  'F#4': 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-fs4.wav',
  A4: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-a4.wav',
  C5: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-c5.wav',
  'D#5': 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-ds5.wav',
  'F#5': 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-fs5.wav',
  A5: 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/969699/s11-a5.wav'
}).connect(lowPass);
sampler.release.value = 2;

let builtInKeyboard = new AudioKeys({ rows: 2 });
let onScreenKeyboardContainer = document.querySelector('.keyboard');
let onScreenKeyboard = buildKeyboard(onScreenKeyboardContainer);
let machinePlayer = buildKeyboard(
  document.querySelector('.machine-bg .player')
);
let humanPlayer = buildKeyboard(document.querySelector('.human-bg .player'));

let currentSeed = [];
let stopCurrentSequenceGenerator;
let synthFilter = new Tone.Filter(300, 'lowpass').connect(
  new Tone.Gain(0.4).connect(masterGain)
);
let synthConfig = {
  oscillator: { type: 'fattriangle' },
  envelope: { attack: 3, sustain: 1, release: 1 }
};
let pulsePattern = true;
let synthsPlaying = {};
let currentPlayFn;
let tick = 0;

let outputs = {
  internal: {
    play: (note, velocity, time, hold = false) => {
      let freq = Tone.Frequency(note, 'midi');
      if (hold) {
        if (!synthsPlaying[note]) {
          let synth = new Tone.Synth(synthConfig).connect(synthFilter);
          synthsPlaying[note] = synth;
          synth.triggerAttack(freq, time, velocity);
        }
      } else {
        sampler.triggerAttack(freq, time);
      }
    },
    stop: (note, time) => {
      if (synthsPlaying[note]) {
        let synth = synthsPlaying[note];
        synth.triggerRelease(time);
        setTimeout(() => synth.dispose(), 2000);
        synthsPlaying[note] = null;
      }
    }
  }
};
let activeOutput = 'internal';

function isAccidental(note) {
  let pc = note % 12;
  return pc === 1 || pc === 3 || pc === 6 || pc === 8 || pc === 10;
}

function buildKeyboard(container) {
  let nAccidentals = _.range(MIN_NOTE, MAX_NOTE + 1).filter(isAccidental)
    .length;
  let keyWidthPercent = 100 / (MAX_NOTE - MIN_NOTE - nAccidentals + 1);
  let keyInnerWidthPercent =
    100 / (MAX_NOTE - MIN_NOTE - nAccidentals + 1) - 0.5;
  let gapPercent = keyWidthPercent - keyInnerWidthPercent;
  let accumulatedWidth = 0;
  return _.range(MIN_NOTE, MAX_NOTE + 1).map(note => {
    let accidental = isAccidental(note);
    let key = document.createElement('div');
    key.classList.add('key');
    if (accidental) {
      key.classList.add('accidental');
      key.style.left = `${accumulatedWidth -
        gapPercent -
        (keyWidthPercent / 2 - gapPercent) / 2}%`;
      key.style.width = `${keyWidthPercent / 2}%`;
    } else {
      key.style.left = `${accumulatedWidth}%`;
      key.style.width = `${keyInnerWidthPercent}%`;
    }
    container.appendChild(key);
    if (!accidental) accumulatedWidth += keyWidthPercent;
    return key;
  });
}

function detectChord(notes) {
  notes = notes.map(n => Tonal.Note.pc(Tonal.Note.fromMidi(n.note))).sort();
  return Tonal.PcSet.modes(notes)
    .map((mode, i) => {
      const tonic = Tonal.Note.name(notes[i]);
      const names = Tonal.Dictionary.chord.names(mode);
      return names.length ? tonic + names[0] : null;
    })
    .filter(x => x);
}

function buildNoteSequence(seed) {
  let step = 0;
  let delayProb = pulsePattern ? 0 : 0.3;
  let notes = seed.map(n => {
    let dur = 1 + (Math.random() < delayProb ? 1 : 0);
    let note = {
      pitch: n.note,
      quantizedStartStep: step,
      quantizedEndStep: step + dur
    };
    step += dur;
    return note;
  });
  return {
    totalQuantizedSteps: _.last(notes).quantizedEndStep,
    quantizationInfo: {
      stepsPerQuarter: 1
    },
    notes
  };
}

function seqToTickArray(seq) {
  return _.flatMap(seq.notes, n =>
    [n.pitch].concat(
      pulsePattern
        ? []
        : _.times(n.quantizedEndStep - n.quantizedStartStep - 1, () => null)
    )
  );
}

function doTick(time = Tone.now() - Tone.context.lookAhead) {
  applyHumanKeyChanges(time);
  if (currentPlayFn) currentPlayFn(time);
}

function startSequenceGenerator(seed) {
  let running = true,
    thisPatternLength = patternLength;

  let chords = detectChord(seed);
  let chord =
    _.first(chords) ||
    Tonal.Note.pc(Tonal.Note.fromMidi(_.first(seed).note)) + 'M';
  let seedSeq = buildNoteSequence(seed);
  let generatedSequence = seqToTickArray(seedSeq);
  let playIntervalTime = Tone.Time('8n').toSeconds();
  let generationIntervalTime = playIntervalTime / 2;
  function generateNext() {
    if (!running) return;
    if (generatedSequence.length < thisPatternLength) {
      rnn.continueSequence(seedSeq, 20, temperature, [chord]).then(genSeq => {
        generatedSequence = generatedSequence.concat(seqToTickArray(genSeq));
        setTimeout(generateNext, generationIntervalTime * 1000);
      });
    }
  }

  tick = 0;
  currentPlayFn = function playNext(time) {
    let tickInSeq = tick % thisPatternLength;
    if (tickInSeq < generatedSequence.length) {
      let note = generatedSequence[tickInSeq];
      if (note) machineKeyDown(note, time);
    }
    tick++;
  };

  setTimeout(generateNext, 0);

  return () => {
    running = false;
    currentPlayFn = null;
  };
}

function updateChord({ add = [], remove = [] }) {
  for (let note of add) {
    currentSeed.push({ note, time: Tone.now() });
  }
  for (let note of remove) {
    _.remove(currentSeed, { note });
  }

  if (stopCurrentSequenceGenerator) {
    stopCurrentSequenceGenerator();
    stopCurrentSequenceGenerator = null;
  }
  if (currentSeed.length) {
    stopCurrentSequenceGenerator = startSequenceGenerator(
      _.cloneDeep(currentSeed)
    );
  }
}

let humanKeyAdds = [],
  humanKeyRemovals = [];
function humanKeyDown(note, velocity = 0.7) {
  if (note < MIN_NOTE || note > MAX_NOTE) return;
  humanKeyAdds.push({ note, velocity });
}
function humanKeyUp(note) {
  if (note < MIN_NOTE || note > MAX_NOTE) return;
  humanKeyRemovals.push({ note });
}
function applyHumanKeyChanges(time = Tone.now()) {
  if (humanKeyAdds.length == 0 && humanKeyRemovals.length == 0) return;
  for (let { note, velocity } of humanKeyAdds) {
    outputs[activeOutput].play(note, velocity, time, true);
    humanPlayer[note - MIN_NOTE].classList.add('down');
    animatePlay(onScreenKeyboard[note - MIN_NOTE], note, true);
  }
  for (let { note } of humanKeyRemovals) {
    outputs[activeOutput].stop(note, time);
    humanPlayer[note - MIN_NOTE].classList.remove('down');
  }
  updateChord({
    add: humanKeyAdds.map(n => n.note),
    remove: humanKeyRemovals.map(n => n.note)
  });
  humanKeyAdds.length = 0;
  humanKeyRemovals.length = 0;
}

function machineKeyDown(note, time) {
  if (note < MIN_NOTE || note > MAX_NOTE) return;
  outputs[activeOutput].play(note, 0.7, time);
  animatePlay(onScreenKeyboard[note - MIN_NOTE], note, false);
  animateMachine(machinePlayer[note - MIN_NOTE]);
}

function animatePlay(keyEl, note, isHuman) {
  let sourceColor = isHuman ? '#1E88E5' : '#E91E63';
  let targetColor = isAccidental(note) ? 'black' : 'white';
  keyEl.animate(
    [{ backgroundColor: sourceColor }, { backgroundColor: targetColor }],
    { duration: 700, easing: 'ease-out' }
  );
}
function animateMachine(keyEl) {
  keyEl.animate([{ opacity: 0.9 }, { opacity: 0 }], {
    duration: Tone.Time('2n').toMilliseconds(),
    easing: 'ease-out'
  });
}

// Computer keyboard controls

builtInKeyboard.down(note => {
  humanKeyDown(note.note);
  hideUI();
});
builtInKeyboard.up(note => humanKeyUp(note.note));

// MIDI Controls

WebMidi.enable(err => {
  if (err) {
    console.error('WebMidi could not be enabled', err);
    return;
  }
  document.querySelector('.midi-not-supported').style.display = 'none';

  let withInputsMsg = document.querySelector('.midi-supported-with-inputs');
  let noInputsMsg = document.querySelector('.midi-supported-no-inputs');
  let inputSelector = document.querySelector('#midi-inputs');
  let outputSelector = document.querySelector('#outputs');
  let clockInputSelector = document.querySelector('#midi-clock-inputs');
  let clockOutputSelector = document.querySelector('#midi-clock-outputs');
  let activeInput,
    activeClockInputId,
    activeClockOutputId,
    transportTickerId,
    clockOutputTickerId,
    midiTickCount,
    lastBeatAt;

  function onInputsChange() {
    if (WebMidi.inputs.length === 0) {
      withInputsMsg.style.display = 'none';
      noInputsMsg.style.display = 'block';
      onActiveInputChange(null);
    } else {
      noInputsMsg.style.display = 'none';
      withInputsMsg.style.display = 'block';
      while (inputSelector.firstChild) {
        inputSelector.firstChild.remove();
      }
      for (let input of WebMidi.inputs) {
        let option = document.createElement('option');
        option.value = input.id;
        option.innerText = input.name;
        inputSelector.appendChild(option);
      }
      onActiveInputChange(WebMidi.inputs[0].id);
    }
  }

  function onOutputsChange() {
    while (outputSelector.firstChild) {
      outputSelector.firstChild.remove();
    }
    let internalOption = document.createElement('option');
    internalOption.value = 'internal';
    internalOption.innerText = 'Internal synth';
    outputSelector.appendChild(internalOption);
    for (let output of WebMidi.outputs) {
      let option = document.createElement('option');
      option.value = output.id;
      option.innerText = output.name;
      outputSelector.appendChild(option);
    }
    onActiveOutputChange('internal');
  }

  function onClockInputsChange() {
    if (WebMidi.inputs.length === 0) {
      onActiveClockInputChange('none');
    } else {
      while (clockInputSelector.firstChild) {
        clockInputSelector.firstChild.remove();
      }
      let option = document.createElement('option');
      option.value = 'none';
      option.innerText = 'None (internal clock)';
      clockInputSelector.appendChild(option);

      for (let input of WebMidi.inputs) {
        option = document.createElement('option');
        option.value = input.id;
        option.innerText = input.name;
        clockInputSelector.appendChild(option);
      }
      onActiveClockInputChange('none');
    }
  }

  function onClockOutputsChange() {
    while (clockOutputSelector.firstChild) {
      clockOutputSelector.firstChild.remove();
    }
    let noneOption = document.createElement('option');
    noneOption.value = 'none';
    noneOption.innerText = 'Not sending';
    clockOutputSelector.appendChild(noneOption);
    for (let output of WebMidi.outputs) {
      let option = document.createElement('option');
      option.value = output.id;
      option.innerText = output.name;
      clockOutputSelector.appendChild(option);
    }
    onActiveClockOutputChange('none');
  }

  function onActiveInputChange(id) {
    if (activeInput) {
      activeInput.removeListener();
    }
    let input = WebMidi.getInputById(id);
    if (input) {
      input.addListener('noteon', 1, e => {
        humanKeyDown(e.note.number, e.velocity);
        hideUI();
      });
      input.addListener('controlchange', 1, e => {
        if (e.controller.number === TEMPO_MIDI_CONTROLLER) {
          Tone.Transport.bpm.value = (e.value / 128) * MAX_MIDI_BPM;
          echo.delayTime.value = Tone.Time('8n.').toSeconds();
        }
      });
      input.addListener('noteoff', 1, e => humanKeyUp(e.note.number));
      for (let option of Array.from(inputSelector.children)) {
        option.selected = option.value === id;
      }
      activeInput = input;
    }
  }

  function onActiveOutputChange(id) {
    if (activeOutput !== 'internal') {
      outputs[activeOutput] = null;
    }
    activeOutput = id;
    if (activeOutput !== 'internal') {
      let output = WebMidi.getOutputById(id);
      outputs[id] = {
        play: (note, velocity = 1, time, hold = false) => {
          if (!hold) {
            let delay = (time - Tone.now()) * 1000;
            let duration = Tone.Time('16n').toMilliseconds();
            output.playNote(note, 'all', {
              time: delay > 0 ? `+${delay}` : WebMidi.now,
              velocity,
              duration
            });
          }
        },
        stop: (note, time) => {
          let delay = (time - Tone.now()) * 1000;
          output.stopNote(note, 2, {
            time: delay > 0 ? `+${delay}` : WebMidi.now
          });
        }
      };
    }
    for (let option of Array.from(outputSelector.children)) {
      option.selected = option.value === id;
    }
  }

  function startClockOutput() {
    let output = WebMidi.getOutputById(activeClockOutputId);
    clockOutputTickerId = Tone.Transport.scheduleRepeat(time => {
      let startDelay = time - Tone.context.currentTime;
      let quarter = Tone.Time('4n').toSeconds();
      for (let i = 0; i < 24; i++) {
        let tickDelay = startDelay + (quarter / 24) * i;
        output.sendClock({ time: `+${tickDelay * 1000}` });
      }
    }, '4n');
  }

  function stopClockOutput() {
    Tone.Transport.clear(clockOutputTickerId);
  }

  function onActiveClockOutputChange(id) {
    if (activeClockOutputId !== 'none') {
      stopClockOutput();
    }
    activeClockOutputId = id;
    if (activeClockOutputId !== 'none') {
      startClockOutput();
    }
    for (let option of Array.from(clockOutputSelector.children)) {
      option.selected = option.value === id;
    }
  }

  function incomingMidiClockStart() {
    midiTickCount = 0;
    tick = 0;
  }

  function incomingMidiClockStop() {
    midiTickCount = 0;
    applyHumanKeyChanges();
  }

  function incomingMidiClockTick(evt) {
    if (midiTickCount % 24 === 0) {
      if (lastBeatAt) {
        let beatDur = evt.timestamp - lastBeatAt;
        Tone.Transport.bpm.value = Math.round(60000 / beatDur);
        // Not sure why this doesn't sync through the BPM automatically. But it doesn't.
        echo.delayTime.value = Tone.Time('8n.').toSeconds();
      }
      lastBeatAt = evt.timestamp;
    }
    if (midiTickCount % 12 === 0) {
      doTick();
    }
    midiTickCount++;
  }

  function onActiveClockInputChange(id) {
    if (activeClockInputId === 'none') {
      Tone.Transport.clear(transportTickerId);
      transportTickerId = null;
    } else if (activeClockInputId) {
      let input = WebMidi.getInputById(activeClockInputId);
      input.removeListener('start', 'all', incomingMidiClockStart);
      input.removeListener('stop', 'all', incomingMidiClockStop);
      input.removeListener('clock', 'all', incomingMidiClockTick);
    }
    activeClockInputId = id;
    if (activeClockInputId === 'none') {
      transportTickerId = Tone.Transport.scheduleRepeat(doTick, '8n');
      Tone.Transport.bpm.value = DEFAULT_BPM;
      echo.delayTime.value = Tone.Time('8n.').toSeconds();
    } else {
      let input = WebMidi.getInputById(id);
      input.addListener('start', 'all', incomingMidiClockStart);
      input.addListener('stop', 'all', incomingMidiClockStop);
      input.addListener('clock', 'all', incomingMidiClockTick);
      midiTickCount = 0;
    }
    for (let option of Array.from(clockInputSelector.children)) {
      option.selected = option.value === id;
    }
  }

  onInputsChange();
  onOutputsChange();
  onClockInputsChange();
  onClockOutputsChange();

  WebMidi.addListener(
    'connected',
    () => (
      onInputsChange(),
      onOutputsChange(),
      onClockInputsChange(),
      onClockOutputsChange()
    )
  );
  WebMidi.addListener(
    'disconnected',
    () => (
      onInputsChange(),
      onOutputsChange(),
      onClockInputsChange(),
      onClockOutputsChange()
    )
  );
  inputSelector.addEventListener('change', evt =>
    onActiveInputChange(evt.target.value)
  );
  outputSelector.addEventListener('change', evt =>
    onActiveOutputChange(evt.target.value)
  );
  clockInputSelector.addEventListener('change', evt =>
    onActiveClockInputChange(evt.target.value)
  );
  clockOutputSelector.addEventListener('change', evt =>
    onActiveClockOutputChange(evt.target.value)
  );
});

// Mouse & touch Controls

let pointedNotes = new Set();

function updateTouchedNotes(evt) {
  let touchedNotes = new Set();
  for (let touch of Array.from(evt.touches)) {
    let element = document.elementFromPoint(touch.clientX, touch.clientY);
    let keyIndex = onScreenKeyboard.indexOf(element);
    if (keyIndex >= 0) {
      touchedNotes.add(MIN_NOTE + keyIndex);
      if (!evt.defaultPrevented) {
        evt.preventDefault();
      }
    }
  }
  for (let note of pointedNotes) {
    if (!touchedNotes.has(note)) {
      humanKeyUp(note);
      pointedNotes.delete(note);
    }
  }
  for (let note of touchedNotes) {
    if (!pointedNotes.has(note)) {
      humanKeyDown(note);
      pointedNotes.add(note);
    }
  }
}

onScreenKeyboard.forEach((noteEl, index) => {
  noteEl.addEventListener('mousedown', evt => {
    humanKeyDown(MIN_NOTE + index);
    pointedNotes.add(MIN_NOTE + index);
    evt.preventDefault();
  });
  noteEl.addEventListener('mouseover', () => {
    if (pointedNotes.size && !pointedNotes.has(MIN_NOTE + index)) {
      humanKeyDown(MIN_NOTE + index);
      pointedNotes.add(MIN_NOTE + index);
    }
  });
});
document.documentElement.addEventListener('mouseup', () => {
  pointedNotes.forEach(n => humanKeyUp(n));
  pointedNotes.clear();
});
document.documentElement.addEventListener('touchstart', updateTouchedNotes);
document.documentElement.addEventListener('touchmove', updateTouchedNotes);
document.documentElement.addEventListener('touchend', updateTouchedNotes);

// Temperature control

let tempSlider = new mdc.slider.MDCSlider(
  document.querySelector('#temperature')
);
tempSlider.listen('MDCSlider:change', () => (temperature = tempSlider.value));
document
  .querySelector('#pattern-length')
  .addEventListener('change', evt => (patternLength = evt.target.value));

// Pulse pattern switch

let pulsePatternControl = new mdc.switchControl.MDCSwitch(
  document.querySelector('.mdc-switch')
);
document
  .querySelector('#pulse-switch')
  .addEventListener('change', evt => (pulsePattern = evt.target.checked));

// Controls hiding

let container = document.querySelector('.container');

function hideUI() {
  container.classList.add('ui-hidden');
}
let scheduleHideUI = _.debounce(hideUI, 5000);
container.addEventListener('mousemove', () => {
  container.classList.remove('ui-hidden');
  scheduleHideUI();
});
container.addEventListener('touchstart', () => {
  container.classList.remove('ui-hidden');
  scheduleHideUI();
});

// Startup

function generateDummySequence() {
  // Generate a throwaway sequence to get the RNN loaded so it doesn't
  // cause jank later.
  return rnn.continueSequence(
    buildNoteSequence([{ note: 60, time: Tone.now() }]),
    20,
    temperature,
    ['Cm']
  );
}

let bufferLoadPromise = new Promise(res => Tone.Buffer.on('load', res));
Promise.all([bufferLoadPromise, rnn.initialize()])
  .then(generateDummySequence)
  .then(() => {
    Tone.Transport.start();
    Tone.Transport.bpm.value = DEFAULT_BPM;
    onScreenKeyboardContainer.classList.add('loaded');
    document.querySelector('.loading').remove();
  });

StartAudioContext(Tone.context, document.documentElement);


