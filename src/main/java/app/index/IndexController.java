package app.index;

import app.util.*;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;
import static app.Application.*;

public class IndexController {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
       // model.put("users", userDao.getAllUserNames());
        model.put("title", "UrsulaGIS");
        FreeMarkerEngine fm= new FreeMarkerEngine();
        return fm.render(new ModelAndView(model, "index.ftl"));
       // model.put("book", bookDao.getRandomBook());
      //  return ViewUtil.render(request, model, Path.Template.INDEX);
    };
}
