package view.adapters;

import control.commands.WorkingDateCommand;
import control.commands.WorkingDaysCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public class WorkingDaysAdapter {
    public static WorkingDaysCommand.Input inputOf(HttpServletRequest request){
        return new WorkingDaysCommand.Input() {
            @Override
            public LocalDate start() {
                return LocalDate.parse(request.getParameter("start"));
            }

            @Override
            public LocalDate end() {
                return LocalDate.parse(request.getParameter("end"));
            }
        };
    }

    public static WorkingDaysCommand.Output outputOf(HttpServletResponse response){
        return day -> {
            try {
                response.setContentType("text/plain");
                response.getWriter().write(String.valueOf(day));
                response.setStatus(200);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
