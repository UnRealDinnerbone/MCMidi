package com.unrealdinnerbone.midiapi.api;

import com.unrealdinnerbone.midiapi.midi.MidiPlayer;
import org.jetbrains.annotations.Nullable;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public class MidiAPI {

	public static MidiPlayer load(String name, Path path) throws InvalidMidiDataException, IOException, MidiUnavailableException {
		MidiPlayer midiSheet = new MidiPlayer(path);
		MidiManger.registerMidiSheet(name, midiSheet);
		return midiSheet;
	}

	@Nullable
	public static MidiPlayer getMidiSheet(String name) {
		return MidiManger.getMidiSheet(name);
	}


	public static Collection<MidiPlayer> getLoadedMidiSheets() {
		return MidiManger.getLoadedMidiSheets();
	}

	public static Collection<String> getLoadedMidiSheetsNames() {
		return MidiManger.getLoadedMidiSheetsName();
	}
	public static void unloadSheet(String name) {
		MidiPlayer sheet = MidiManger.getMidiSheet(name);
		if(sheet != null) {
			sheet.stop();
			MidiManger.unloadMidiSheet(name);
		}
	}
}
