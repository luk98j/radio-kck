package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.*;

public class RadioChannels {
    private MediaPlayer rockPlayer;
    private MediaPlayer jazzPlayer;
    private MediaPlayer hiphopPlayer;
    private MediaPlayer classicPlayer;
    private MediaPlayer popPlayer;
    private MediaPlayer technoPlayer;
    private MediaPlayer radioTuningPlayer;
    List<MediaPlayer> players = new ArrayList<>();
    private static final double ROCK_FM = 88.5;
    private static final double JAZZ_FM = 89.5;
    private static final double HIPHOP_FM = 90.1;
    private static final double CLASSIC_FM = 94.5;
    private static final double POP_FM = 96.4;
    private static final double TECHNO_FM = 99.4;
    private HashMap<String,Double> stationsMap;

    public RadioChannels() {
        stationsMap = new HashMap<>();
        stationsMap.put("ROCK_FM",ROCK_FM);
        stationsMap.put("JAZZ_FM",JAZZ_FM);
        stationsMap.put("HIPHOP_FM",HIPHOP_FM);
        stationsMap.put("CLASSIC_FM",CLASSIC_FM);
        stationsMap.put("POP_FM",POP_FM);
        stationsMap.put("TECHNO_FM",TECHNO_FM);
        String rock = "src/sample/sounds/ROCK_FM.mp3";     // For example
        Media sound = new Media(new File(rock).toURI().toString());
        this.rockPlayer = new MediaPlayer(sound);
        rockPlayer.play();
        rockPlayer.setMute(true);
        rockPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        String jazz = "src/sample/sounds/JAZZ_FM.mp3";     // For example
        Media sound_j = new Media(new File(jazz).toURI().toString());
        this.jazzPlayer = new MediaPlayer(sound_j);
        jazzPlayer.play();
        jazzPlayer.setMute(true);
        jazzPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        String hipHop = "src/sample/sounds/HIPHOP_FM.mp3";     // For example
        Media sound_h = new Media(new File(hipHop).toURI().toString());
        this.hiphopPlayer = new MediaPlayer(sound_h);
        hiphopPlayer.play();
        hiphopPlayer.setMute(true);
        hiphopPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        String classic = "src/sample/sounds/CLASSIC_FM.mp3";     // For example
        Media sound_c = new Media(new File(classic).toURI().toString());
        this.classicPlayer = new MediaPlayer(sound_c);
        classicPlayer.play();
        classicPlayer.setMute(true);
        classicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        String pop = "src/sample/sounds/POP_FM.mp3";     // For example
        Media sound_p = new Media(new File(pop).toURI().toString());
        this.popPlayer = new MediaPlayer(sound_p);
        popPlayer.play();
        popPlayer.setMute(true);
        popPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        String techno = "src/sample/sounds/TECHNO_FM.mp3";     // For example
        Media sound_t = new Media(new File(techno).toURI().toString());
        this.technoPlayer = new MediaPlayer(sound_t);
        technoPlayer.play();
        technoPlayer.setMute(true);
        technoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        String tuning = "src/sample/sounds/Radio_Tuning.mp3";     // For example
        Media sound_tuning = new Media(new File(tuning).toURI().toString());
        this.radioTuningPlayer = new MediaPlayer(sound_tuning);
        radioTuningPlayer.play();
        radioTuningPlayer.setMute(true);
        radioTuningPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        players.add(rockPlayer);
        players.add(jazzPlayer);
        players.add(hiphopPlayer);
        players.add(classicPlayer);
        players.add(popPlayer);
        players.add(technoPlayer);
        players.add(radioTuningPlayer);
        players.stream().forEach(mediaPlayer -> mediaPlayer.setVolume(0.5));
    }

    public String playMusic(double frequency){
        if(frequency == ROCK_FM){
            playChannel("ROCK_FM");
            return "ROCK_FM";
        }
        else if(frequency == JAZZ_FM){
            playChannel("JAZZ_FM");
            return "JAZZ_FM";
        }
        else if(frequency == HIPHOP_FM){
            playChannel("HIPHOP_FM");
            return "HIPHOP_FM";
        }
        else if(frequency == CLASSIC_FM){
            playChannel("CLASSIC_FM");
            return "CLASSIC_FM";
        }
        else if(frequency == POP_FM){
            playChannel("POP_FM");
            return "POP_FM";
        }
        else if(frequency == TECHNO_FM){
            playChannel("TECHNO_FM");
            return "TECHNO_FM";
        }
        else {
            playChannel("RADIO_TUNING");
            stopPlaying();
            return String.valueOf(frequency);
        }
    }

    private void playChannel(String channel){
        if(rockPlayer.isMute() && channel.equals("ROCK_FM")) {
            radioTuningPlayer.setMute(true);
            rockPlayer.setMute(false);
        } else if (jazzPlayer.isMute() && channel.equals("JAZZ_FM")){
            radioTuningPlayer.setMute(true);
            jazzPlayer.setMute(false);
        }
        else if (hiphopPlayer.isMute() && channel.equals("HIPHOP_FM")){
            radioTuningPlayer.setMute(true);
            hiphopPlayer.setMute(false);
        }
        else if (classicPlayer.isMute() && channel.equals("CLASSIC_FM")){
            radioTuningPlayer.setMute(true);
            classicPlayer.setMute(false);
        }
        else if (popPlayer.isMute() && channel.equals("POP_FM")){
            radioTuningPlayer.setMute(true);
            popPlayer.setMute(false);
        }
        else if (technoPlayer.isMute() && channel.equals("TECHNO_FM")){
            radioTuningPlayer.setMute(true);
            technoPlayer.setMute(false);
        } else if (radioTuningPlayer.isMute() && channel.equals("RADIO_TUNING")){
            radioTuningPlayer.setMute(false);
        }
    }

    private void stopPlaying(){
        try {
            for (MediaPlayer player:players) {
                if(!player.equals(radioTuningPlayer))
                player.setMute(true);
            }

        } catch (NullPointerException e){

        }
    }
    public void stopAll(){
        try {
            for (MediaPlayer player:players) {
                player.setMute(true);
            }
        } catch (NullPointerException e){

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
        for (MediaPlayer player: players) {
            if(!player.equals(radioTuningPlayer) && player.getVolume() != 1){
                player.setVolume(player.getVolume()+0.1);
            }
        }
    }
    public void volumeDown(){
        for (MediaPlayer player: players) {
            if(!player.equals(radioTuningPlayer) && player.getVolume() != 0){
                player.setVolume(player.getVolume()-0.1);
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
}
