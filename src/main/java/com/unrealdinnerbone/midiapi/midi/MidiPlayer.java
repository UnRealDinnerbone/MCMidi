package com.unrealdinnerbone.midiapi.midi;

import net.kyori.adventure.sound.Sound;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MidiPlayer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MidiPlayer.class);
	private final Map<Integer, Integer> patches = new HashMap<>();

	private final List<Player> playerPlayers = new ArrayList<>();

	private final List<Location> locationPlayers = new ArrayList<>();

	private final Sequencer sequencer;

	public MidiPlayer(Path path) throws InvalidMidiDataException, IOException, MidiUnavailableException {

		this.sequencer = MidiSystem.getSequencer(false);
		sequencer.open();
		Sequence sequence = MidiSystem.getSequence(path.toFile());
		sequencer.setSequence(sequence);


		sequencer.getTransmitter().setReceiver(new Receiver() {

			@Override
			public void send(MidiMessage message, long timeStamp) {
				if(message instanceof ShortMessage msg) {
					switch (message.getStatus() & 0xF0) {
						case ShortMessage.PROGRAM_CHANGE -> patches.put(msg.getChannel(), msg.getData1());
						case ShortMessage.NOTE_ON -> {
							int chan = msg.getChannel();
							//I don't know what it this really should be called
							int n = msg.getData1();

							byte instrument = chan == 9 ? MidiUtils.toMCPercussion(patches.get(chan)) : MidiUtils.toMCInstrument(patches.get(chan));
							byte pitch = MidiUtils.toMCNote(n);
							float velocity = (msg.getData2() / 127f);

							Sound sound = MidiUtils.getSound(instrument, pitch, velocity);

							playerPlayers.forEach(p -> p.playSound(sound));
							locationPlayers.forEach(l -> l.getWorld().playSound(sound, l.x(), l.y(), l.z()));

						}
					}

				}

			}

			@Override
			public void close() {

			}
		});


	}

	public void isPlaying() {
		sequencer.isRunning();
	}
	public void start() {
		sequencer.start();
	}

	public void stop() {
		sequencer.stop();
	}

	public void reset() {
		sequencer.setTickPosition(0);
	}
	public void addPlayer(Player p) {
		playerPlayers.add(p);
	}
	public void removePlayer(Player p) {
		playerPlayers.remove(p);
	}
	public void addLocation(Location l) {
		locationPlayers.add(l);
	}
	public void removeLocation(Location l) {
		locationPlayers.remove(l);
	}



}
