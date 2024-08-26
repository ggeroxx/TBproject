package service.strategy;

import java.sql.SQLException;
import java.util.ArrayList;
import service.CategoryService;

public interface Strategy 
{
    String execute(CategoryService service, ArrayList<Object> params) throws SQLException;
}