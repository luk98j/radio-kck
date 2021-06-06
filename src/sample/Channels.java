package sample;

public class Channels {
    private static final double ROCK_FM = 88.5;
    private static final double JAZZ_FM = 89.5;
    private static final double HIPHOP_FM = 90.1;
    private static final double CLASSIC_FM = 94.5;
    private static final double POP_FM = 96.4;
    private static final double TECHNO_FM = 99.4;

    public String playMusic(double frequency){
        if(frequency == ROCK_FM){
            return "ROCK FM";
        }
    }
}
