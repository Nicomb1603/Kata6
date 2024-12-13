package view.adapters;

import control.commands.WorkingDateCommand;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public class WorkingDateAdapter {
    public static WorkingDateCommand.Input inputOf(HttpServletRequest request){
        return new WorkingDateCommand.Input() {
            @Override
            public LocalDate start() {
                return LocalDate.parse(request.getParameter("start"));
            }

            @Override
            public int workingDays() {
                return Integer.parseInt(request.getParameter("days"));
            }
        };
    }

    public static WorkingDateCommand.Output outputOf(HttpServletResponse response){
        return date -> {
            try {
                response.setContentType("text/plain");
                response.getWriter().write(date.toString());
                response.setStatus(200);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
