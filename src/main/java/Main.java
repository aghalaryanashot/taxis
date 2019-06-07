package main.java;

import main.java.Client;
import main.java.Logic;

public class Main {
    public static void main(String[] args) {
        String reposInMass = args[0];
        int quantityTaxi = Integer.parseInt(args[1]);
        String reposOutXml = args[2];
        int quantityDispatch = Integer.parseInt(args[3]);

        Client client = new Client(reposInMass);
        Logic logic = new Logic(client,quantityTaxi,reposOutXml,quantityDispatch);
        logic.run();
    }
}


