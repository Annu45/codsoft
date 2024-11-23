import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int totalRoundsWon = 0;
        int maxAttempts = 5;
        boolean playAgain = true;
        
        System.out.println("Welcome to the Number Guessing Game!");
        
        
        while (playAgain) {
            int targetNumber = random.nextInt(100) + 1; 
            int attempts = 0;
            boolean correctGuess = false;
            
            System.out.println("\nNew Round! Try to guess the number between 1 and 100.");
            while (attempts < maxAttempts && !correctGuess) {
                System.out.print("Enter your guess (Attempt " + (attempts + 1) + " of " + maxAttempts + "): ");
                int playerGuess = scanner.nextInt();
                attempts++;
                
                if (playerGuess == targetNumber) {
                    System.out.println("Congratulations! You've guessed the number correctly.");
                    correctGuess = true;
                    totalRoundsWon++;
                } else if (playerGuess < targetNumber) {
                    System.out.println("Too low! Try a higher number.");
                } else {
                    System.out.println("Too high! Try a lower number.");
                }
            }
            
            if (!correctGuess) {
                System.out.println("Sorry, you've run out of attempts! The correct number was: " + targetNumber);
            }
            System.out.print("\nWould you like to play again? (yes/no): ");
            scanner.nextLine();  
            String response = scanner.nextLine().toLowerCase();
            playAgain = response.equals("yes");
        }
        System.out.println("\nGame Over! You won " + totalRoundsWon + " rounds. Thanks for playing!");
        scanner.close();
    }
}

