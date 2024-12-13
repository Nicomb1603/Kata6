package view;

import control.CommandFactory;
import control.commands.WorkingDateCommand;
import control.commands.WorkingDaysCommand;
import io.javalin.Javalin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import view.adapters.WorkingDateAdapter;
import view.adapters.WorkingDaysAdapter;

public class WorkingDaysService {
    private final int port;
    private final CommandFactory factory;
    public Javalin app;

    public WorkingDaysService(int port, CommandFactory factory) {
        this.port = port;
        this.factory = factory;
        factory.register("working-days", workingDaysBuilder());
        factory.register("working-date", workingDateBuilder());
    }

    private CommandFactory.Builder workingDaysBuilder() {

        return (request, response) -> new WorkingDaysCommand(WorkingDaysAdapter.inputOf(request), WorkingDaysAdapter.outputOf(response));
    }

    private CommandFactory.Builder workingDateBuilder() {

        return (request, response) -> new WorkingDateCommand(WorkingDateAdapter.inputOf(request), WorkingDateAdapter.outputOf(response));
    }

    public void start(){
        app = Javalin.create()
                .get("/", ctx -> ctx.result("Comandos disponibles:\n /working-days\n /working-date"))
                .get("/working-days", ctx -> execute("working-days", ctx.req(), ctx.res()))
                .get("/working-date", ctx -> execute("working-date", ctx.req(), ctx.res()))
                .start(port);
    }

    private void execute(String command, HttpServletRequest request, HttpServletResponse response) {
        factory.with(request, response).build(command).execute();
    }

    public void stop(){app.stop();}

}
