package projectClass;

import java.sql.SQLException;

public interface ProposalJDBC {

    void insertProposal ( Category requestedCategory, Category offeredCategory, int requestedHours, int offeredHours, User user, String state ) throws SQLException;

}
