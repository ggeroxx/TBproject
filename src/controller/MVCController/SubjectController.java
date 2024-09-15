package controller.MVCController;

import java.sql.SQLException;
import service.SubjectService;

public class SubjectController {
    
    private SubjectService subjectService;

    public SubjectController ( SubjectService subjectService ) 
    {
        this.subjectService = subjectService;
    }

    public boolean isPresentUsername ( String usernameToCheck ) throws SQLException
    {
        return this.subjectService.isPresentUsername(usernameToCheck);
    }

}
