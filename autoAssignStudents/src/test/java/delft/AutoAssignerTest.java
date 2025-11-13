package delft;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import java.time.*;

class AutoAssignerTest {

    ZonedDateTime d1 = date(1992, 9, 22, 23, 59);
    ZonedDateTime d2 = date(1993, 8, 20, 12, 33);

    private HashMap<ZonedDateTime, Integer> sperd = new HashMap<>();
    private HashMap<ZonedDateTime, Integer> sperd2 = new HashMap<>();

    private Student thomas = new Student(1, "thomas","graboulette@gmail.com");
    private Student anas = new Student(2, "anas","anasnasdas@gmail.com");
    private List<Student> students = new ArrayList<>();

    private Workshop w1;
    private Workshop w2;
    private List<Workshop> workshops;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    AutoAssigner autoAssigner = new AutoAssigner();

    private ZonedDateTime date(int year, int month, int day, int hour, int minute) {
        return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, ZoneId.systemDefault());
    }


    @Test
    public void testNoPlaceForOneDateForTwoWorkshops(){
        students.add(thomas);
        students.add(anas);


        sperd.put(d1, 1);
        sperd.put(d2, 0);

        sperd2.put(d1, 0);
        sperd2.put(d2, 30);

        w1 = new Workshop(1, "activité crampté",sperd);
        w2 = new Workshop(2, "bootcamp corée",sperd2);

        workshops = List.of(w1, w2);


        AssignmentsLogger logg = autoAssigner.assign(students, workshops);
        List<String> logs = logg.getErrors();
        List<String> assignments = logg.getAssignments();
        assertThat(logs).hasSize(1);

        assertThat(logs).contains("activité crampté,anas");
        assertThat(assignments).contains(String.format("%s,%s,%s", "activité crampté", "thomas", d1.format(formatter)));
    }

    @Test
    public void testNoSpotsAtAll(){
        students.add(thomas);
        students.add(anas);


        sperd.put(d1, 0);
        sperd.put(d2, 0);

        sperd2.put(d1, 0);
        sperd2.put(d2, 0);

        w1 = new Workshop(1, "activités cramptés",sperd);
        w2 = new Workshop(2, "bootcamp corée",sperd2);

        workshops = List.of(w1, w2);

        AssignmentsLogger logg = autoAssigner.assign(students, workshops);
        List<String> logs = logg.getErrors();
        assertThat(logs).hasSize(4);



        assertThat(logs).contains("activités cramptés,thomas");
        assertThat(logs).contains("activités cramptés,anas");
        assertThat(logs).contains("bootcamp corée,thomas");
        assertThat(logs).contains("bootcamp corée,anas");

    }

    @Test
    public void testValidStudent(){
        assertEquals("graboulette@gmail.com", thomas.getEmail());
        assertEquals("thomas", thomas.getName());
        assertEquals(1, thomas.getId());
    }





}
