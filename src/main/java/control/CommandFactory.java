package control;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private final Map<String, Builder> builders;

    public CommandFactory() {
        this.builders = new HashMap<>();
    }

    public void register(String name, Builder builder){builders.put(name, builder);}

    public Selector with(HttpServletRequest request, HttpServletResponse response){
        return name -> builders.get(name).build(request, response);
    }

    public interface Builder {
        Command build(HttpServletRequest request, HttpServletResponse response);
    }

    public interface Selector {
        Command build(String name);
    }
}
