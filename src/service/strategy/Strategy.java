package service.strategy;

import java.sql.SQLException;

import service.CategoryService;

public interface Strategy 
{
    String execute(CategoryService service, Object... params) throws SQLException;
}