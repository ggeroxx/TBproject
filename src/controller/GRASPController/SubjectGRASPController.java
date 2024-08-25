package controller.GRASPController;

import java.sql.SQLException;
import service.SubjectService;

public class SubjectGRASPController {
    
    private SubjectService subjectService;

    public SubjectGRASPController ( SubjectService subjectService ) 
    {
        this.subjectService = subjectService;
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        return this.subjectService.isPresentUsername(usernameToCheck);
    }

}
