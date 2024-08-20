package controller.GRASPController;

import java.sql.*;
import service.*;

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
