package com.unrealdinnerbone.midiapi.api;

import com.unrealdinnerbone.midiapi.midi.MidiPlayer;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MidiManger
{
    private static final Map<String, MidiPlayer> midiMusic = new ConcurrentHashMap<>();

    public static void registerMidiSheet(String name, MidiPlayer midi) {
        midiMusic.put(name, midi);
    }

    public static MidiPlayer getMidiSheet(String name) {
        return midiMusic.get(name);
    }
    public static Collection<String> getLoadedMidiSheetsName() {
        return midiMusic.keySet();
    }

    public static Collection<MidiPlayer> getLoadedMidiSheets() {
        return midiMusic.values();
    }

    public static void unloadMidiSheet(String name) {
        midiMusic.remove(name);
    }



}
