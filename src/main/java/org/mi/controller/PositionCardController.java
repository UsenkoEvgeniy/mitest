package org.mi.controller;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mi.service.PositionCardService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/card")
public class PositionCardController extends HttpServlet {
    private static Gson gson = new Gson();
    private static PositionCardService service = new PositionCardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupId = req.getParameter("groupid");
        String str = service.getName(groupId);
        HashMap<String, String> ans = new HashMap<>();
        ans.put("name", str);
        PrintWriter pw = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        pw.print(gson.toJson(ans));
        pw.flush();
    }
}
