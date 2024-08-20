package service.strategy;

import java.sql.SQLException;


import model.*;

import service.CategoryService;
import util.Constants;

public class BuildHierarchyStrategy implements Strategy {

    @Override
    public String execute(CategoryService service, Object... params) throws SQLException {
        Category category = (Category) params[0];
        int IDToPrint = (int) params[1];
        StringBuffer toReturn = (StringBuffer) params[2];
        StringBuffer spaces = (StringBuffer) params[3];

        Category notLeaf = service.getCategoryRepository().getCategoryByID(IDToPrint);

        if (notLeaf.isRoot()) {
            toReturn.append(notLeaf.getID() + ". " + notLeaf.getName() + "\n\n");
        } else if (service.getCategoryRepository().getCategoriesWithoutChild().contains(notLeaf)) {
            toReturn.append(spaces.toString() + Constants.RED + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + Constants.NO_CHILD + Constants.RESET + "\n\n");
        } else {
            toReturn.append(spaces.toString() + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + "\n\n");
        }

        for (Integer IDLeaf : service.getRelationshipsBetweenCategoriesRepository().getChildIDsFromParentID(IDToPrint)) {
            execute(service, category, IDLeaf, toReturn, spaces.append("\t"));
        }

        if (spaces.length() > 1) spaces.setLength(spaces.length() - 1);
        else spaces.setLength(0);

        return toReturn.toString();
    }

}