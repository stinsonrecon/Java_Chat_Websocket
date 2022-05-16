
import Model.User;
import MongodbHelper.DataHelper;
import Functions.PostData;
import Model.Temp;
import MongodbHelper.MongoHelper;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mac
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, Exception {
        MongoHelper mongoHelper = new MongoHelper();
        String token = DataHelper.generateNewToken();
        Scanner scanner = new Scanner(System.in);
        
        PostData postData = new PostData();
        
        //User user = new User("123", token,"tien");
        //TransferDataHelper.putDataTo_PosteeDB_Register(user);
        //TransferDataHelper.createCollections("tienListMess");
        //TransferDataHelper.dropCollections("a");
         //TransferDataHelper.dropCollections("b");
//        System.out.println("Enter username: ");
//        String user = scanner.nextLine();
//        System.out.println("Enter password: ");
//        String password = scanner.nextLine();
        
        DataHelper.getIdByFields("username", "a1");
        //System.out.println(postData.GetUsername(autho));
        
    }

}
