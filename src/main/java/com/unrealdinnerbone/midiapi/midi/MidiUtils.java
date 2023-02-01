package com.unrealdinnerbone.midiapi.midi;

import net.kyori.adventure.sound.Sound;
import org.bukkit.Instrument;

public class MidiUtils {

    private static final byte[] instruments = {
            0, 0, 0, 0, 0, 0, 0,11, // 0-7
            6, 6, 6, 6, 9, 9,15,11, // 8-15
            10,5, 5,10,10,10,10,10, // 16-23
            5, 5, 5, 5, 5, 5, 5, 5, // 24-31
            1, 1, 1, 1, 1, 1, 1, 1, // 32-39
            0,10,10, 1, 0, 0, 0, 4, // 40-47
            0, 0, 0, 0, 8, 8, 8,12, // 48-55
            8,14,14,14,14,14, 8, 8, // 56-63
            8, 8, 8,14,14, 8, 8, 8, // 64-71
            8, 8, 8, 8,14, 8, 8, 8, // 72-79
            8,14, 8, 8, 5, 8,12, 1, // 80-87
            1, 0, 0, 8, 0, 0, 0, 0, // 88-95
            0, 0, 7, 0, 0, 0, 0,12, // 96-103
            11,11,3, 3, 3,14,10, 6, // 104-111
            6, 3, 3, 2, 2, 2, 6, 5, // 112-119
            1, 1, 1,13,13, 2, 4, 7, // 120-127
    };


    private static final byte[] percussion = {
            9, 6, 4, 4, 3, 2, 3, 2, //40 - Electric Snare
            2, 2, 2, 2, 2, 2, 2, 2, //48 - Hi Mid Tom
            7, 2, 7, 7, 6, 3, 7, 6, //56 - Cowbell
            7, 3, 7, 2, 2, 3, 3, 3, //64 - Low Conga
            2, 2, 6, 6, 2, 2, 0, 0, //72 - Long Whistle
            3, 3, 3, 3, 3, 3, 5, 5, //80 - Open Cuica
            15, 15,                 //82 - Open Triangle
    };


    public static byte toMCInstrument(Integer patch) {
        if (patch == null) {
            return 0;
        }

        if (patch < 0 || patch >= instruments.length) {
            return 0;
        }

        return instruments[patch];
    }

    public static byte toMCPercussion(Integer patch) {
        if(patch == null) {
            return 0;
        }

        int i = patch - 33;
        if (i < 0 || i >= percussion.length) {
            return 1;
        }

        return percussion[i];
    }

    public static byte toMCNote(int n) {
        if (n < 54) {
            return (byte) ((n - 6) % (18 - 6));
        } else if (n > 78) {
            return (byte) ((n - 6) % (18 - 6) + 12);
        } else {
            return (byte) (n - 54);
        }
    }

    public static Sound getSound(byte instrument, byte pitch, float velocity) {
        float adjustedPitch = (float) Math.pow(2.0D, (pitch - 12) / 12.0D);

        return net.kyori.adventure.sound.Sound
                .sound()
                .pitch(adjustedPitch)
                .volume(velocity)
                .source(net.kyori.adventure.sound.Sound.Source.RECORD)
                .type(toSound(fromByte(instrument)))
                .build();
    }

    private static org.bukkit.Sound toSound(Instrument instrument) {
        return switch (instrument) {
            case BASS_DRUM -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_BASEDRUM;
            case SNARE_DRUM -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_SNARE;
            case STICKS -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_HAT;
            case BANJO -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_BANJO;
            case BASS_GUITAR -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_BASS;
            case BELL -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_BELL;
            case BIT -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_BIT;
            case CHIME -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_CHIME;
            case COW_BELL -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_COW_BELL;
            case DIDGERIDOO -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO;
            case FLUTE -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_FLUTE;
            case XYLOPHONE -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_XYLOPHONE;
            case IRON_XYLOPHONE -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE;
            case PLING -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING;
            case GUITAR -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_GUITAR;
            default -> org.bukkit.Sound.BLOCK_NOTE_BLOCK_HARP;
        };
    }

    public static Instrument fromByte(byte instrument) {
                return switch (instrument) {
            case 1 -> Instrument.BASS_DRUM;
            case 2 -> Instrument.SNARE_DRUM;
            case 3 -> Instrument.STICKS;
            case 4 -> Instrument.BASS_GUITAR;
            case 5 -> Instrument.GUITAR;
            case 6 -> Instrument.BELL;
            case 7 -> Instrument.CHIME;
            case 8 -> Instrument.FLUTE;
            case 9 -> Instrument.XYLOPHONE;
            case 10 -> Instrument.PLING;
            case 11 -> Instrument.BANJO;
            case 12 -> Instrument.BIT;
            case 13 -> Instrument.COW_BELL;
            case 14 -> Instrument.DIDGERIDOO;
            case 15 -> Instrument.IRON_XYLOPHONE;
            default -> Instrument.PIANO;
        };
    }


}
