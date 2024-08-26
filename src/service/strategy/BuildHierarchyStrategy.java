package service.strategy;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Category;
import model.util.Constants;
import service.CategoryService;

public class BuildHierarchyStrategy implements Strategy {

    @Override
    public String execute(CategoryService service, ArrayList<Object> params) throws SQLException 
    {
        int IDToPrint = (int) params.get(0);
        StringBuffer toReturn = (StringBuffer) params.get(1);
        StringBuffer spaces = (StringBuffer) params.get(2);

        Category notLeaf = service.getCategoryRepository().getCategoryByID(IDToPrint);

        if (notLeaf.isRoot()) {
            toReturn.append(notLeaf.getID() + ". " + notLeaf.getName() + "\n\n");
        } else if (service.getCategoryRepository().getCategoriesWithoutChild().contains(notLeaf)) {
            toReturn.append(spaces.toString() + Constants.RED + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + Constants.NO_CHILD + Constants.RESET + "\n\n");
        } else {
            toReturn.append(spaces.toString() + "└──── " + notLeaf.getID() + ". " + notLeaf.getName() + "\n\n");
        }

        for (Integer IDLeaf : service.getRelationshipsBetweenCategoriesRepository().getChildIDsFromParentID(IDToPrint)) 
        {
            ArrayList<Object> newParams = new ArrayList<>();
            newParams.add(IDLeaf);
            newParams.add(toReturn);
            newParams.add(spaces.append("\t"));
            execute(service, newParams);
        }

        if (spaces.length() > 1) spaces.setLength(spaces.length() - 1);
        else spaces.setLength(0);

        return toReturn.toString();
    }

}