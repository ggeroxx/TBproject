package service.strategy;

import java.sql.SQLException;
import model.Category;
import model.util.Constants;
import service.CategoryService;

public class InfoStrategy implements Strategy {

    @Override
    public String execute(CategoryService service, Object... params) throws SQLException {
        Category category = (Category) params[0];
        StringBuffer sb = new StringBuffer();

        sb.append(Constants.NAME + ":" + service.padRight(Constants.NAME + ":", 20) + Constants.BOLD + category.getName() + Constants.RESET + "\n");
        if (!category.isRoot()) sb.append(Constants.DESCRIPTION + ":" + service.padRight(Constants.DESCRIPTION + ":", 20) + category.getDescription() + "\n");

        if (category.getField() == null) {
            sb.append(Constants.VALUE_OF_DOMAIN + ":" + service.padRight(Constants.VALUE_OF_DOMAIN + ":", 20) + service.getRelationshipsBetweenCategoriesRepository().getFieldValueFromChildID(category.getID()) + "\n");
        } else {
            sb.append(Constants.FIELD + ":" + service.padRight(Constants.FIELD + ":", 20) + category.getField() + " = { ");
            for (String fieldValue : service.getRelationshipsBetweenCategoriesRepository().getFieldValuesFromParentID(category.getID())) {
                sb.append(Constants.YELLOW + fieldValue + Constants.RESET + ", ");
            }
            sb.deleteCharAt(sb.length() - 2);
            sb.append("}\n");
        }

        return sb.toString();
    }
}