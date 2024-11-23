import java.util.*;
import java.util.concurrent.*;

public class QuizApplication {

    static class Question {
        String question;
        String[] options;
        int correctAnswerIndex;

        Question(String question, String[] options, int correctAnswerIndex) {
            this.question = question;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }

    static class TimerTask implements Runnable {
        private final long timeLimit;
        private boolean isTimeUp;

        TimerTask(long timeLimit) {
            this.timeLimit = timeLimit;
            this.isTimeUp = false;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(timeLimit);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            isTimeUp = true;
        }

        boolean isTimeUp() {
            return isTimeUp;
        }
    }

    private static List<Question> quizQuestions = new ArrayList<>();
    private static int score = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadQuestions();
        startQuiz();
        displayResult();
    }

    private static void loadQuestions() {
        quizQuestions.add(new Question(
            "What is the capital of France?",
            new String[]{"Berlin", "Madrid", "Paris", "Rome"},
            2
        ));
        quizQuestions.add(new Question(
            "Which planet is known as the Red Planet?",
            new String[]{"Earth", "Mars", "Jupiter", "Saturn"},
            1
        ));
        quizQuestions.add(new Question(
            "Which programming language is this quiz written in?",
            new String[]{"Python", "Java", "C++", "JavaScript"},
            1
        ));
    }

    private static void startQuiz() {
        for (int i = 0; i < quizQuestions.size(); i++) {
            Question currentQuestion = quizQuestions.get(i);
            System.out.println("Question " + (i + 1) + ": " + currentQuestion.question);
            for (int j = 0; j < currentQuestion.options.length; j++) {
                System.out.println((j + 1) + ". " + currentQuestion.options[j]);
            }

            TimerTask timerTask = new TimerTask(10);
            Thread timerThread = new Thread(timerTask);
            timerThread.start();

            System.out.println("\nYou have " + 10 + " seconds to answer...");
            System.out.print("Select an option (1-4): ");
            
            String answer = null;
            boolean answered = false;
            while (!answered && !timerTask.isTimeUp()) {
                if (scanner.hasNext()) {
                    answer = scanner.nextLine();
                    if (isValidAnswer(answer)) {
                        answered = true;
                    } else {
                        System.out.print("Invalid option. Please choose between 1 and 4: ");
                    }
                }
            }

            if (!answered) {
                System.out.println("\nTime's up!");
            }

            int answerIndex = answer != null ? Integer.parseInt(answer) - 1 : -1;
            if (answerIndex == currentQuestion.correctAnswerIndex) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer was: "
                        + currentQuestion.options[currentQuestion.correctAnswerIndex] + "\n");
            }
        }
    }

    private static boolean isValidAnswer(String answer) {
        try {
            int option = Integer.parseInt(answer);
            return option >= 1 && option <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void displayResult() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score: " + score + "/" + quizQuestions.size());
    }
}

