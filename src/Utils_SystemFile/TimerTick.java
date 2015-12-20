package Utils_SystemFile;

import Database.DataBase;
import Output.OutputController;
import Utils_SystemFile.InputBasicData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class TimerTick implements ActionListener {

    int nowTimeSec;
    OutputController outputter;
    //行動を起こすまでの時間
    int actionTime = 20;

    DataBase database;
    Timer thisController = null;

    public TimerTick(OutputController outputter, DataBase database) {
        this.outputter = outputter;
        this.database = database;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nowTimeSec++;
        System.out.println("経過時間：" + nowTimeSec);
        if (database.check() == 1 && nowTimeSec % actionTime == 0) {
            String output = null;
            try {
                output = outputter.getTimeAction();
            } catch (IOException ex) {
                Logger.getLogger(TimerTick.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(output);
        }
    }

    

    public void setController(Timer controller) {
        thisController = controller;
    }
}
