import com.baddragon.Port.Port;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PortTest {

    @Test
    public void compareDatesTestTrue(){
        Date date1 = new Date();
        Date date2 = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(10));

        boolean res = Port.getInstance().compareDates(date1.getTime(), date2.getTime());
        System.out.println("date2 greater then date1");
        Assertions.assertEquals(res, true);

    }

    @Test
    public void compareDatesTestFalse(){
        Date date1 = new Date();
        Date date2 = new Date(new Date().getTime() - TimeUnit.DAYS.toMillis(10));

        boolean res = Port.getInstance().compareDates(date1.getTime(), date2.getTime());
        System.out.println("date1 greater then date2");
        Assertions.assertEquals(res, false);

    }

    @Test
    public void compareDatesTestTrueEq(){
        Date date1 = new Date();

        boolean res = Port.getInstance().compareDates(date1.getTime(), date1.getTime());
        System.out.println("date1 eq date2");
        Assertions.assertEquals(res, true);

    }

    @Test
    public void removeFromList(){

        List<Boolean> trues = new ArrayList<>();
        trues.add(true);
        trues.add(true);
        trues.add(false);
        trues.add(true);
        trues.add(false);
        trues.add(true);
        trues.add(false);
        List<Integer> idToRemove = new ArrayList<>();
        idToRemove.add(2);
        idToRemove.add(4);
        idToRemove.add(6);
        System.out.println(trues);
        Port.getInstance().removeFromListAt(trues, idToRemove);
        System.out.println(trues);
        Assertions.assertEquals(trues, Arrays.asList(true, true, true, true));

    }

}
