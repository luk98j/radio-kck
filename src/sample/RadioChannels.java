package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RadioChannels {
    private final MediaPlayer rockPlayer;
    private final MediaPlayer jazzPlayer;
    private final MediaPlayer hiphopPlayer;
    private final MediaPlayer classicPlayer;
    private final MediaPlayer popPlayer;
    private final MediaPlayer technoPlayer;
    private final MediaPlayer radioTuningPlayer;

    private static final double ROCK_FM = 88.5;
    private static final double JAZZ_FM = 89.5;
    private static final double HIPHOP_FM = 90.1;
    private static final double CLASSIC_FM = 94.5;
    private static final double POP_FM = 96.4;
    private static final double TECHNO_FM = 99.4;
    private HashMap<String,Double> stationsMap;
    private HashMap<String,MediaPlayer> playerHashMap;
    private final Logger log;

    public RadioChannels() {
        log = Logger.getLogger(getClass().getName());
        stationsMap = new HashMap<>();
        playerHashMap = new HashMap<>();
        stationsMap.put("ROCK_FM",ROCK_FM);
        stationsMap.put("JAZZ_FM",JAZZ_FM);
        stationsMap.put("HIPHOP_FM",HIPHOP_FM);
        stationsMap.put("CLASSIC_FM",CLASSIC_FM);
        stationsMap.put("POP_FM",POP_FM);
        stationsMap.put("TECHNO_FM",TECHNO_FM);
        this.radioTuningPlayer = createPlayer("RADIO_TUNING");
        this.rockPlayer = createPlayer("ROCK_FM");
        this.jazzPlayer = createPlayer("JAZZ_FM");
        this.hiphopPlayer = createPlayer("HIPHOP_FM");
        this.classicPlayer = createPlayer("CLASSIC_FM");
        this.popPlayer = createPlayer("POP_FM");
        this.technoPlayer = createPlayer("TECHNO_FM");
        playerHashMap.put("ROCK_FM",rockPlayer);
        playerHashMap.put("JAZZ_FM",jazzPlayer);
        playerHashMap.put("HIPHOP_FM",hiphopPlayer);
        playerHashMap.put("CLASSIC_FM",classicPlayer);
        playerHashMap.put("POP_FM",popPlayer);
        playerHashMap.put("TECHNO_FM",technoPlayer);
        playerHashMap.put("RADIO_TUNING",radioTuningPlayer);
    }

    public String playMusic(double frequency){
        Optional<Map.Entry<String, Double>> optional = stationsMap.entrySet().stream()
                .filter(stringDoubleEntry -> frequency == stringDoubleEntry.getValue()).findFirst();
        if (optional.isPresent()){
            playChannel(optional.get().getKey());
            return optional.get().getKey();
        } else {
            playChannel("RADIO_TUNING");
            stopPlaying();
            return String.valueOf(frequency);
        }
    }

    private void playChannel(String channel){
        for (Map.Entry<String, MediaPlayer> entry:playerHashMap.entrySet()) {
            if(!entry.getValue().equals(radioTuningPlayer) && entry.getValue().isMute() && channel.equals(entry.getKey())){
                radioTuningPlayer.setMute(true);
                entry.getValue().setMute(false);
            } else if (radioTuningPlayer.isMute() && channel.equals(entry.getKey())){
                radioTuningPlayer.setMute(false);
            }
        }
    }

    private void stopPlaying(){
        try {
            for (Map.Entry<String, MediaPlayer> entry:playerHashMap.entrySet()) {
                if(!entry.getValue().equals(radioTuningPlayer))
                entry.getValue().setMute(true);
            }
        } catch (NullPointerException e){
            log.log(Level.INFO,"The problem with mutating player {}", e.getClass());
        }
    }
    public void stopAll(){
        try {
            for (Map.Entry<String, MediaPlayer> entry:playerHashMap.entrySet()) {
                entry.getValue().setMute(true);
            }
        } catch (NullPointerException e){
            log.log(Level.INFO,"The problem with mutating player {}", e.getClass());
        }
    }

    public double getStationFrequency(String station){
        stopAll();
        if(stationsMap.containsKey(station)){
            return stationsMap.get(station);
        } else {
            return Double.parseDouble(station);
        }
    }

    public void volumeUp(){
        for (Map.Entry<String, MediaPlayer> entry:playerHashMap.entrySet()) {
            if(!entry.getValue().equals(radioTuningPlayer) && entry.getValue().getVolume() != 1){
                entry.getValue().setVolume(entry.getValue().getVolume()+0.1);
            }
        }
    }
    public void volumeDown(){
        for (Map.Entry<String, MediaPlayer> entry:playerHashMap.entrySet()) {
            if(!entry.getValue().equals(radioTuningPlayer) && entry.getValue().getVolume() != 1){
                entry.getValue().setVolume(entry.getValue().getVolume()-0.1);
            }
        }
    }

    public Map.Entry<String, Double> findNearestStation(double frequency){
        Map.Entry<String, Double> station = stationsMap.entrySet().stream()
                .min(Comparator.comparingDouble(i -> Math.abs(i.getValue() - frequency)))
                .orElseThrow(()-> new NoSuchElementException("No station"));
        playMusic(station.getValue());
        return station;
    }

    private MediaPlayer createPlayer(String song){
        String songPath = "src/sample/sounds/"+song+".mp3";
        Media sound = new Media(new File(songPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlayer.setMute(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.5);
        return mediaPlayer;
    }
}
