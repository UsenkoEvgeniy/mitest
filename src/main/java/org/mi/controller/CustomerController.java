package org.mi.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mi.model.Customer;
import org.mi.service.CustomerService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collectors;

@WebServlet("/customers")
public class CustomerController extends HttpServlet {
    private static Gson gson = new Gson();
    private static CustomerService service = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqBody = req.getReader().lines().collect(Collectors.joining());
        Properties data = gson.fromJson(reqBody, Properties.class);
        Integer offset = Integer.valueOf(data.getProperty("offset"));
        Collection<Customer> customers = service.getCustomers(offset);
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(customers));
        out.flush();
    }
}
