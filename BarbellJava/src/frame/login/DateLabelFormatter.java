package frame.login;
// 운동기록 날짜 라이브러리 윤선호
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

      private String datePattern = "yyyy/MM/dd";
      private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

      @Override
      public Object stringToValue(String text) throws ParseException {
         return dateFormatter.parseObject(text);
      }

      @Override
      public String valueToString(Object value) throws ParseException {
         if (value != null) {
            try {
               Calendar cal = (Calendar) value;
               
               return dateFormatter.format(cal.getTime());               
            } catch (Exception e) {
               // TODO: handle exception
            }
         }

         return "";
      }

   }