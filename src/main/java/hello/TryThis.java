package hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class TryThis {

    private static  String[] roles = new String [2];

    private static final Logger log = LogManager.getLogger(TryThis.class);

    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
    private static double calculateSpeedAve(int hours, int minutes, int seconds, double distance){
        //calculate ave Speed and Pace
        Duration duration = Duration.ZERO.plus(hours, HOURS).plus(minutes, MINUTES).plus(seconds, SECONDS);
        double totalSecs = duration.getSeconds();
        int secsInHour= 3600;
        double kmPerHour = distance / (totalSecs/secsInHour);
        double kmPerHourRoundOff = (double) Math.round(kmPerHour * 10) / 10;
        return kmPerHourRoundOff;
    }

    private static String calculatePaceAve(int hours, int minutes, int seconds, double distance){
        //calculate ave Speed and Pace
        Duration duration = Duration.ZERO.plus(hours, HOURS).plus(minutes, MINUTES).plus(seconds, SECONDS);
        double totalSecs = duration.getSeconds();
        double secsPerKm = totalSecs / distance;
        int secsPerKmRoundOff = (int) Math.round(secsPerKm);
        Duration paceSecs = Duration.ZERO.plus(secsPerKmRoundOff,SECONDS);
        return paceSecs.toMinutes()+":"+paceSecs.getSeconds() % 60;
    }


    public static void durationSample() {
        //Let's say duration of 2days 3hours 12minutes and 46seconds
        Duration d = Duration.ZERO.plus(1, HOURS).plus(25, MINUTES).plus(35, SECONDS);
        System.out.println(d);
        Double distance = 16.5;
        Double aveSecs = new Double(d.getSeconds());
        Double secsH= new Double(3600);
        Double aveH =aveSecs/secsH;
        double kmPh = distance/aveH;
        double roundOff = (double) Math.round(kmPh * 10) / 10;

        System.out.println(calculateSpeedAve(1,25,35, distance));
        System.out.println(calculatePaceAve(1,25,35, distance));

        System.out.println("aveH="+aveH+":::"+"kmPh="+roundOff);

        long inHours = d.getSeconds();
        Double inH =new Double(7306/3600);
        System.out.println("d.getSeconds()/3600="+inH);
        System.out.println(7306/3600);
        System.out.println("inHours="+inHours);
        System.out.println("SecsPerKm="+d.getSeconds()/distance);
        Double SecsPerKm=d.getSeconds()/distance;
        System.out.println("SecsPerKm="+SecsPerKm.longValue());
        System.out.println("SecsPerKm="+SecsPerKm.shortValue());

        Duration avePace = Duration.ZERO.plus(SecsPerKm.longValue(),SECONDS);
        System.out.println("avePace="+avePace.toMinutes()+":"+avePace.getSeconds() % 60 );
        //format DAYS HOURS MINUTES SECONDS
        System.out.printf("Total duration is %sdays %shrs %smin %ssec.\n", d.toDays(), d.toHours() % 24, d.toMinutes() % 60, d.getSeconds() % 60);

        //or format HOURS MINUTES SECONDS
        System.out.printf("Or total duration is %shrs %smin %sec.\n", d.toHours(), d.toMinutes() % 60, d.getSeconds() % 60);

        //or format MINUTES SECONDS
        System.out.printf("Or total duration is %smin %ssec.\n", d.toMinutes(), d.getSeconds() % 60);

        //or format SECONDS only
        System.out.printf("Or total duration is %ssec.\n", d.getSeconds());

    }

    public static void main(String[] args) {

        durationSample();





        Duration duration = Duration.parse("PT3H23M10s");
      //  long duration = 4 * 60 * 60 * 1000;
       // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
       // log.info("Duration: " + sdf.format(new Date(duration - TimeZone.getDefault().getRawOffset())));
        log.info(duration);
log.info(formatDuration(duration));
       /* Duration diff = Duration.parse("1:03:03");
                String hms = String.format("%d:%02d:%02d",
                diff.toHoursPart(),
                diff.toMinutesPart(),
                diff.toSecondsPart());
        */
        String datestr = new String("2008-11-20 17:45:19");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(datestr, formatter);

        System.out.println("dateTime="+dateTime);


        LocalDateTime ldt = LocalDateTime.now();
        System.out.println("ldt.getMonthValue()="+ldt.getMonthValue());
        System.out.println(DateTimeFormatter.ofPattern("MM-dd-yyyy").format(ldt));
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").format(LocalDateTime.now()));
        System.out.println(ldt);

        roles[0]="ADMIN";
        roles[1]="USER";

        System.out.println("roles="+roles[0]+";"+roles[1]);

        for (int i=0;i<roles.length;i++)
            System.out.println("roles["+i+"]="+roles[i]);

    }
}
