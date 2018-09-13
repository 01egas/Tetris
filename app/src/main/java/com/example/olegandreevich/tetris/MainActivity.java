package com.example.olegandreevich.tetris;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    Game game;
    static final String LEFT = "left";
    static final String RIGHT = "right";
    static final String ROTATE = "rotate";

    static int current0;
    static int current1;
    static int currentShapeInt;
    static int nextShapeInt;
    static int score;
    static int speedOfShape;
    static int countOfShapes;
    static int level;

    static Button[][] nextShape;

    public static boolean resetGame = false;

    int[][] shapeForHandle = new int[4][2];

    int[][] shape0;
    int[][] shape1;
    int[][] shape2;
    int[][] shape3;
    int[][] shape4;
    int[][] shape5;
    int[][] shape6;


    static int[][] currentShape;
    Button btLeft, btRight, btRotate, btPlay, btReset,
            bt00, bt01, bt02, bt03, bt04, bt05, bt06, bt07, bt08, bt09,
            bt10, bt11, bt12, bt13, bt14, bt15, bt16, bt17, bt18, bt19,
            bt20, bt21, bt22, bt23, bt24, bt25, bt26, bt27, bt28, bt29,
            bt30, bt31, bt32, bt33, bt34, bt35, bt36, bt37, bt38, bt39,
            bt40, bt41, bt42, bt43, bt44, bt45, bt46, bt47, bt48, bt49,
            bt50, bt51, bt52, bt53, bt54, bt55, bt56, bt57, bt58, bt59,
            bt60, bt61, bt62, bt63, bt64, bt65, bt66, bt67, bt68, bt69,
            bt70, bt71, bt72, bt73, bt74, bt75, bt76, bt77, bt78, bt79,
            bt80, bt81, bt82, bt83, bt84, bt85, bt86, bt87, bt88, bt89,
            bt90, bt91, bt92, bt93, bt94, bt95, bt96, bt97, bt98, bt99,
            bt100, bt101, bt102, bt103, bt104, bt105, bt106, bt107, bt108, bt109,
            bt110, bt111, bt112, bt113, bt114, bt115, bt116, bt117, bt118, bt119,
            bt120, bt121, bt122, bt123, bt124, bt125, bt126, bt127, bt128, bt129,
            bt130, bt131, bt132, bt133, bt134, bt135, bt136, bt137, bt138, bt139,
            bt140, bt141, bt142, bt143, bt144, bt145, bt146, bt147, bt148, bt149,
            bt150, bt151, bt152, bt153, bt154, bt155, bt156, bt157, bt158, bt159,
            bt160, bt161, bt162, bt163, bt164, bt165, bt166, bt167, bt168, bt169,
            bt170, bt171, bt172, bt173, bt174, bt175, bt176, bt177, bt178, bt179,
            bt180, bt181, bt182, bt183, bt184, bt185, bt186, bt187, bt188, bt189,
            bt190, bt191, bt192, bt193, bt194, bt195, bt196, bt197, bt198, bt199,
            btNextShape00, btNextShape01, btNextShape02, btNextShape03,
            btNextShape10, btNextShape11, btNextShape12, btNextShape13,
            btNextShape20, btNextShape21, btNextShape22, btNextShape23,
            btNextShape30, btNextShape31, btNextShape32, btNextShape33;
    LinearLayout mainWindowGame;
    TextView totalScore, tvLevel;


    int[][][] shapesArray;// = {shape1, shape2, shape3, shape4, shape5, shape6, shape7};

    Button[][] gameBoard;

    int[] fillingLines;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtonsOnGameBoard();
        btLeft = findViewById(R.id.buttonLeft);
        btLeft.setOnClickListener(this);

        btRight = findViewById(R.id.buttonRight);
        btRight.setOnClickListener(this);

        btRotate = findViewById(R.id.buttonRotate);
        btRotate.setOnClickListener(this);

        btPlay = findViewById(R.id.btPlay);
        btPlay.setOnClickListener(this);

        btReset = findViewById(R.id.btReset);
        btReset.setOnClickListener(this);

        speedOfShape = 500;
        countOfShapes = 0;
        level = 1;

        totalScore = findViewById(R.id.tvTotalScore);
        totalScore.setText("Score: " + score);

        tvLevel = findViewById(R.id.tvLevel);
        tvLevel.setText("Level: " + level);

        score = 0;
        currentShape = new int[4][2];

        fillingLines = new int[20];

        mainWindowGame = findViewById(R.id.mainLayout);


        shape0 = new int[][]{{0, 3}, {1, 3}, {1, 4}, {1, 5}}; // |__
        shape1 = new int[][]{{0, 5}, {1, 3}, {1, 4}, {1, 5}}; // __|
        shape2 = new int[][]{{0, 4}, {1, 3}, {1, 4}, {1, 5}}; // .!.
        shape3 = new int[][]{{0, 4}, {0, 5}, {1, 3}, {1, 4}}; // _-
        shape4 = new int[][]{{0, 3}, {0, 4}, {1, 4}, {1, 5}}; // -_
        shape5 = new int[][]{{0, 4}, {0, 5}, {1, 4}, {1, 5}}; // #
        shape6 = new int[][]{{0, 4}, {1, 4}, {2, 4}, {3, 4}}; // |
        shapesArray = new int[][][]{shape0, shape1, shape2, shape3, shape4, shape5, shape6};

        nextShapeInt = ThreadLocalRandom.current().nextInt(0, 7);

        gameBoard = new Button[][]{
                {bt00, bt01, bt02, bt03, bt04, bt05, bt06, bt07, bt08, bt09},
                {bt10, bt11, bt12, bt13, bt14, bt15, bt16, bt17, bt18, bt19},
                {bt20, bt21, bt22, bt23, bt24, bt25, bt26, bt27, bt28, bt29},
                {bt30, bt31, bt32, bt33, bt34, bt35, bt36, bt37, bt38, bt39},
                {bt40, bt41, bt42, bt43, bt44, bt45, bt46, bt47, bt48, bt49},
                {bt50, bt51, bt52, bt53, bt54, bt55, bt56, bt57, bt58, bt59},
                {bt60, bt61, bt62, bt63, bt64, bt65, bt66, bt67, bt68, bt69},
                {bt70, bt71, bt72, bt73, bt74, bt75, bt76, bt77, bt78, bt79},
                {bt80, bt81, bt82, bt83, bt84, bt85, bt86, bt87, bt88, bt89},
                {bt90, bt91, bt92, bt93, bt94, bt95, bt96, bt97, bt98, bt99},
                {bt100, bt101, bt102, bt103, bt104, bt105, bt106, bt107, bt108, bt109},
                {bt110, bt111, bt112, bt113, bt114, bt115, bt116, bt117, bt118, bt119},
                {bt120, bt121, bt122, bt123, bt124, bt125, bt126, bt127, bt128, bt129},
                {bt130, bt131, bt132, bt133, bt134, bt135, bt136, bt137, bt138, bt139},
                {bt140, bt141, bt142, bt143, bt144, bt145, bt146, bt147, bt148, bt149},
                {bt150, bt151, bt152, bt153, bt154, bt155, bt156, bt157, bt158, bt159},
                {bt160, bt161, bt162, bt163, bt164, bt165, bt166, bt167, bt168, bt169},
                {bt170, bt171, bt172, bt173, bt174, bt175, bt176, bt177, bt178, bt179},
                {bt180, bt181, bt182, bt183, bt184, bt185, bt186, bt187, bt188, bt189},
                {bt190, bt191, bt192, bt193, bt194, bt195, bt196, bt197, bt198, bt199}
        };

        nextShape = new Button[][]{
                {btNextShape00, btNextShape01, btNextShape02, btNextShape03},
                {btNextShape10, btNextShape11, btNextShape12, btNextShape13},
                {btNextShape20, btNextShape21, btNextShape22, btNextShape23},
                {btNextShape30, btNextShape31, btNextShape32, btNextShape33}
        };


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLeft:
                handleOfDirection(LEFT);
                break;
            case R.id.buttonRight:
                handleOfDirection(RIGHT);
                break;
            case R.id.buttonRotate:
                handleOfDirection(ROTATE);
                break;
            case R.id.btPlay:
                createShape();
                runThread();
                break;
            case R.id.btReset:
                resetGame();
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    public void createShape() {
        if (game != null && game.isCancelled()) {
            resetGame();
        }
        int[][] shape = choiceNewShape();
        countOfShapes++;
        nextShapeInt = ThreadLocalRandom.current().nextInt(0, 7);
        showNextShape();
//        currentShapeInt = 1;
//        int[][] shape = shapesArray[1];
        for (int i = 0; i < shape.length; i++) {
            currentShape[i][0] = shape[i][0];
            currentShape[i][1] = shape[i][1];
            shapeForHandle[i][0] = shape[i][0];
            shapeForHandle[i][1] = shape[i][1];

            if (gameBoard[shape[i][0]][shape[i][1]].getVisibility() != View.VISIBLE) {
                gameBoard[shape[i][0]][shape[i][1]].setVisibility(View.VISIBLE);

            } else {
                game.cancel(false);
            }

        }


    }

    public void resetGame() {
        game.cancel(false);
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j].setVisibility(View.INVISIBLE);
            }
        }
        speedOfShape = 500;
        countOfShapes = 0;
        level = 1;
        cleanNextShape();
        nextShapeInt = ThreadLocalRandom.current().nextInt(0, 7);

        currentShape = new int[4][2];
        shapeForHandle = new int[4][2];


    }

    public void showNextShape() {
        cleanNextShape();
        int[][] sh = shapesArray[nextShapeInt];
        for (int i = 0; i < 4; i++) {
            nextShape[sh[i][0]][sh[i][1] - 3].setVisibility(View.VISIBLE);
        }
    }

    public void cleanNextShape() {
        for (int j = 0; j < nextShape.length; j++) {
            for (int g = 0; g < nextShape[j].length; g++) {
                nextShape[j][g].setVisibility(View.INVISIBLE);
            }
        }
    }

    public class Game extends AsyncTask {

        public Game() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected synchronized Object doInBackground(Object[] objects) {



//                if (resetGame) {
//                    for (int i = gameBoard.length - 1; i >= 0; i--) {
//                        current0 = i;
//                        visInvis = 0;
//                        publishProgress();
//
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    for (int i = 0; i < gameBoard.length; i++) {
//
//                        current0 = i;
//                        visInvis = 4;
//                        publishProgress();
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//
//
//                }

                    while (!isCancelled()) {
                        if (countOfShapes == 50) {
                            speedOfShape -= 50;
                            countOfShapes = 0;
                            level++;
                            tvLevel.setText("Level: " + level);
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(speedOfShape);
                            publishProgress();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }



                return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


        @Override
        protected void onProgressUpdate(Object[] values) {
            synchronized (currentShape) {


                super.onProgressUpdate(values);


//                if (resetGame) {
//                    for (int j = 0; j < gameBoard[current0].length; j++) {
//                        current1 = j;
//                        gameBoard[current0][current1].setVisibility(visInvis);
//                    }
//                }
                    boolean itDraw = true;


                    for (int[] coordinatesOfCurrentButton : currentShape) {
                        current0 = coordinatesOfCurrentButton[0];
                        current1 = coordinatesOfCurrentButton[1];
                        if (coordinatesOfCurrentButton[0] != 19
                                && (gameBoard[current0 + 1][current1].getVisibility() == View.INVISIBLE || checkCoordinates(current0 + 1, current1, currentShape))) {
                            continue;
                        } else {
                            itDraw = false;
                            incrementFillLines();
                            checkFillLines();
                            createShape();
                            break;
                        }

                    }
                    if (itDraw) {
                        setAttributeInvisible();
                        for (int i = currentShape.length - 1; i >= 0; i--) {
                            currentShape[i][0] = currentShape[i][0] + 1;
                        }
                        setAttributeVisible();
                    }

            }
        }


    }

    public void checkFillLines() {
        int countOfCleanLines = 0;
        for (int i = fillingLines.length - 1; i >= 0; i--) {
            if (fillingLines[i] == 10) {
                countOfCleanLines++;
                score += 10;
                totalScore.setText("Score: " + score);
            } else if (countOfCleanLines > 0) {
                for (int j = 0; j < 10; j++) {
                    gameBoard[i + countOfCleanLines][j].setVisibility(gameBoard[i][j].getVisibility());
                    gameBoard[i][j].setVisibility(View.INVISIBLE);
                }
                fillingLines[i + countOfCleanLines] = fillingLines[i];
                fillingLines[i] = 0;

            }
        }
    }

    public void incrementFillLines() {
        for (int i = 0; i < currentShape.length; i++) {
            fillingLines[currentShape[i][0]]++;
        }

    }

    public boolean checkCoordinates(int x, int y, int[][] checkArray) {

        boolean isCheckTrue = false;
        for (int[] ch : checkArray) {
            if (x == ch[0] && y == ch[1]) {
                isCheckTrue = true;
                break;

            }
        }
        return isCheckTrue;
    }

    public void runThread() {
        resetGame = false;
        game = new Game();

        game.execute();

    }

    public void setAttributeInvisible() {
        gameBoard[currentShape[0][0]][currentShape[0][1]].setVisibility(View.INVISIBLE);
        gameBoard[currentShape[1][0]][currentShape[1][1]].setVisibility(View.INVISIBLE);
        gameBoard[currentShape[2][0]][currentShape[2][1]].setVisibility(View.INVISIBLE);
        gameBoard[currentShape[3][0]][currentShape[3][1]].setVisibility(View.INVISIBLE);
    }

    public void setAttributeVisible() {
        gameBoard[currentShape[0][0]][currentShape[0][1]].setVisibility(View.VISIBLE);
        gameBoard[currentShape[1][0]][currentShape[1][1]].setVisibility(View.VISIBLE);
        gameBoard[currentShape[2][0]][currentShape[2][1]].setVisibility(View.VISIBLE);
        gameBoard[currentShape[3][0]][currentShape[3][1]].setVisibility(View.VISIBLE);
    }

    public int[][] choiceNewShape() {

        currentShapeInt = nextShapeInt;
        return shapesArray[currentShapeInt];
    }



    public boolean checkIndexOutOfBounds() {
        boolean check = false;

        int[] arrayForSort0 = new int[4];
        int[] arrayForSort1 = new int[4];

        int module0 = -10;
        int module1 = -10;
        for (int i = 0; i < shapeForHandle.length; i++) {

            arrayForSort0[i] = shapeForHandle[i][0];
            arrayForSort1[i] = shapeForHandle[i][1];

        }
        Arrays.sort(arrayForSort0);
        Arrays.sort(arrayForSort1);

        if (arrayForSort0[0] < 0) {
            module0 = -arrayForSort0[0];
        } else if (arrayForSort0[3] > 19) {
            module0 = 19 - arrayForSort0[3];
        } else if (module0 == -10) {
            module0 = 0;
        }
        if (arrayForSort1[0] < 0) {
            module1 = -arrayForSort1[0];
        } else if (arrayForSort1[3] > 9) {
            module1 = 9 - arrayForSort1[3];
        } else if (module1 == -10) {
            module1 = 0;
        }
        if (canDraw(module0, module1)) {
            shapeForHandle[0][0] += module0;
            shapeForHandle[1][0] += module0;
            shapeForHandle[2][0] += module0;
            shapeForHandle[3][0] += module0;

            shapeForHandle[0][1] += module1;
            shapeForHandle[1][1] += module1;
            shapeForHandle[2][1] += module1;
            shapeForHandle[3][1] += module1;

            check = true;
        }
        return check;

    }

    public boolean canDraw(int movement0, int movement1) {
        boolean itDraw = true;
        for (int[] coordinatesOfCurrentButton : shapeForHandle) {

            current0 = coordinatesOfCurrentButton[0] + movement0;
            current1 = coordinatesOfCurrentButton[1] + movement1;

            if (gameBoard[current0][current1].getVisibility() == View.INVISIBLE || checkCoordinates(current0, current1, MainActivity.currentShape)) {
                continue;
            } else {
                itDraw = false;
                break;
            }

        }
        return itDraw;
    }

    public void handleOfDirection(String move) {
        synchronized (currentShape) {


            boolean itDraw = true;

            switch (move) {
                case LEFT:
                    for (int[] coordinatesOfCurrentButton : currentShape) {
                        current0 = coordinatesOfCurrentButton[0];
                        current1 = coordinatesOfCurrentButton[1];
                        if (coordinatesOfCurrentButton[1] != 0
                                && (gameBoard[current0][current1 - 1].getVisibility() == View.INVISIBLE || checkCoordinates(current0, current1 - 1, MainActivity.currentShape))) {
                            continue;
                        } else {
                            itDraw = false;
                            break;
                        }

                    }
                    if (itDraw) {
                        setAttributeInvisible();
                        for (int i = 0; i < currentShape.length; i++) {
                            MainActivity.currentShape[i][1] = MainActivity.currentShape[i][1] - 1;
                        }
                        setAttributeVisible();
                    }

                    break;
                case RIGHT:
                    for (int[] coordinatesOfCurrentButton : currentShape) {
                        current0 = coordinatesOfCurrentButton[0];
                        current1 = coordinatesOfCurrentButton[1];
                        if (coordinatesOfCurrentButton[1] == 9
                                || (gameBoard[current0][current1 + 1].getVisibility() != View.INVISIBLE && !checkCoordinates(current0, current1 + 1, MainActivity.currentShape))) {
                            itDraw = false;
                            break;
                        }

                    }
                    if (itDraw) {
                        setAttributeInvisible();
                        for (int i = currentShape.length - 1; i >= 0; i--) {
                            MainActivity.currentShape[i][1] = MainActivity.currentShape[i][1] + 1;
                        }
                        setAttributeVisible();
                    }

                    break;
                case ROTATE:

                    switch (currentShapeInt) {
                        case 0:
                            if (currentShape[2][0] - 1 == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0];
                                shapeForHandle[0][1] = currentShape[0][1] + 2;
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;


                            } else if (currentShape[2][0] - 1 == currentShape[0][0] && currentShape[2][1] + 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0] + 2;
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;


                            } else if (currentShape[2][0] + 1 == currentShape[0][0] && currentShape[2][1] + 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0];
                                shapeForHandle[0][1] = currentShape[0][1] - 2;
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;

                            } else if (currentShape[2][0] + 1 == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {


                                setAttributeInvisible();
                                shapeForHandle[0][0] = currentShape[0][0] - 2;
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;


                            }
                            Toast.makeText(getApplicationContext(), "|__", Toast.LENGTH_SHORT).show();

                            break;
                        case 1:
                            if (currentShape[2][0] - 1 == currentShape[0][0] && currentShape[2][1] + 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0] + 2;
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;


                            } else if (currentShape[2][0] + 1 == currentShape[0][0] && currentShape[2][1] + 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0];
                                shapeForHandle[0][1] = currentShape[0][1] - 2;
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;


                            } else if (currentShape[2][0] + 1 == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0] - 2;
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;


                            } else if (currentShape[2][0] - 1 == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {


                                shapeForHandle[0][0] = currentShape[0][0];
                                shapeForHandle[0][1] = currentShape[0][1] + 2;
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;


                            }
                            Toast.makeText(getApplicationContext(), "__|", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            if (currentShape[2][0] - 1 == currentShape[0][0] && currentShape[2][1] == currentShape[0][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] + 1;
                                shapeForHandle[0][1] = currentShape[0][1] + 1;
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;

                            } else if (currentShape[2][0] == currentShape[0][0] && currentShape[2][1] + 1 == currentShape[0][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] + 1;
                                shapeForHandle[0][1] = currentShape[0][1] - 1;
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;

                            } else if (currentShape[2][0] + 1 == currentShape[0][0] && currentShape[2][1] == currentShape[0][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] - 1;
                                shapeForHandle[0][1] = currentShape[0][1] - 1;
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;

                            } else if (currentShape[2][0] == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] - 1;
                                shapeForHandle[0][1] = currentShape[0][1] + 1;
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;

                            }
                            Toast.makeText(getApplicationContext(), ".:.", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            if (currentShape[0][0] == currentShape[1][0] && currentShape[0][1] + 1 == currentShape[1][1]) {

                                shapeForHandle[0][0] = currentShape[0][0];
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1] + 2;
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;

                            } else if (currentShape[0][0] - 1 == currentShape[1][0] && currentShape[0][1] == currentShape[1][1]) {

                                shapeForHandle[0][0] = currentShape[0][0];
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1] - 2;
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;

                            }
                            Toast.makeText(getApplicationContext(), "_-", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            if (currentShape[2][0] - 1 == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] + 2;
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;

                            } else if (currentShape[2][0] + 1 == currentShape[0][0] && currentShape[2][1] - 1 == currentShape[0][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] - 2;
                                shapeForHandle[0][1] = currentShape[0][1];
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;

                            }
                            Toast.makeText(getApplicationContext(), "-_", Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
                            shapeForHandle[0][0] = currentShape[0][0];
                            shapeForHandle[0][1] = currentShape[0][1];
                            shapeForHandle[1][0] = currentShape[1][0];
                            shapeForHandle[1][1] = currentShape[1][1];
                            shapeForHandle[2][0] = currentShape[2][0];
                            shapeForHandle[2][1] = currentShape[2][1];
                            shapeForHandle[3][0] = currentShape[3][0];
                            shapeForHandle[3][1] = currentShape[3][1];

                            Toast.makeText(getApplicationContext(), "#", Toast.LENGTH_SHORT).show();
                            break;
                        case 6:
                            if (currentShape[2][0] + 1 == currentShape[3][0] && currentShape[2][1] == currentShape[3][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] + 2;
                                shapeForHandle[0][1] = currentShape[0][1] + 2;
                                shapeForHandle[1][0] = currentShape[1][0] + 1;
                                shapeForHandle[1][1] = currentShape[1][1] + 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] - 1;
                                shapeForHandle[3][1] = currentShape[3][1] - 1;

                            } else if (currentShape[2][0] == currentShape[3][0] && currentShape[2][1] - 1 == currentShape[3][1]) {

                                shapeForHandle[0][0] = currentShape[0][0] - 2;
                                shapeForHandle[0][1] = currentShape[0][1] - 2;
                                shapeForHandle[1][0] = currentShape[1][0] - 1;
                                shapeForHandle[1][1] = currentShape[1][1] - 1;
                                shapeForHandle[2][0] = currentShape[2][0];
                                shapeForHandle[2][1] = currentShape[2][1];
                                shapeForHandle[3][0] = currentShape[3][0] + 1;
                                shapeForHandle[3][1] = currentShape[3][1] + 1;

                            }
                            Toast.makeText(getApplicationContext(), "____", Toast.LENGTH_SHORT).show();
                            break;

                    }
                    if (checkIndexOutOfBounds()) {
                        setAttributeInvisible();
                        for (int i = 0; i < shapeForHandle.length; i++) {
                            currentShape[i][0] = shapeForHandle[i][0];
                            currentShape[i][1] = shapeForHandle[i][1];
                        }
                        setAttributeVisible();
                    }


            }

        }
    }


    public void initializeButtonsOnGameBoard() {
        btNextShape00 = findViewById(R.id.btNextShape00);
        btNextShape01 = findViewById(R.id.btNextShape01);
        btNextShape02 = findViewById(R.id.btNextShape02);
        btNextShape03 = findViewById(R.id.btNextShape03);
        btNextShape10 = findViewById(R.id.btNextShape10);
        btNextShape11 = findViewById(R.id.btNextShape11);
        btNextShape12 = findViewById(R.id.btNextShape12);
        btNextShape13 = findViewById(R.id.btNextShape13);
        btNextShape20 = findViewById(R.id.btNextShape20);
        btNextShape21 = findViewById(R.id.btNextShape21);
        btNextShape22 = findViewById(R.id.btNextShape22);
        btNextShape23 = findViewById(R.id.btNextShape23);
        btNextShape30 = findViewById(R.id.btNextShape30);
        btNextShape31 = findViewById(R.id.btNextShape31);
        btNextShape32 = findViewById(R.id.btNextShape32);
        btNextShape33 = findViewById(R.id.btNextShape33);

        bt00 = findViewById(R.id.button00);
        bt01 = findViewById(R.id.button01);
        bt02 = findViewById(R.id.button02);
        bt03 = findViewById(R.id.button03);
        bt04 = findViewById(R.id.button04);
        bt05 = findViewById(R.id.button05);
        bt06 = findViewById(R.id.button06);
        bt07 = findViewById(R.id.button07);
        bt08 = findViewById(R.id.button08);
        bt09 = findViewById(R.id.button09);
        bt10 = findViewById(R.id.button10);
        bt11 = findViewById(R.id.button11);
        bt12 = findViewById(R.id.button12);
        bt13 = findViewById(R.id.button13);
        bt14 = findViewById(R.id.button14);
        bt15 = findViewById(R.id.button15);
        bt16 = findViewById(R.id.button16);
        bt17 = findViewById(R.id.button17);
        bt18 = findViewById(R.id.button18);
        bt19 = findViewById(R.id.button19);
        bt20 = findViewById(R.id.button20);
        bt21 = findViewById(R.id.button21);
        bt22 = findViewById(R.id.button22);
        bt23 = findViewById(R.id.button23);
        bt24 = findViewById(R.id.button24);
        bt25 = findViewById(R.id.button25);
        bt26 = findViewById(R.id.button26);
        bt27 = findViewById(R.id.button27);
        bt28 = findViewById(R.id.button28);
        bt29 = findViewById(R.id.button29);
        bt30 = findViewById(R.id.button30);
        bt31 = findViewById(R.id.button31);
        bt32 = findViewById(R.id.button32);
        bt33 = findViewById(R.id.button33);
        bt34 = findViewById(R.id.button34);
        bt35 = findViewById(R.id.button35);
        bt36 = findViewById(R.id.button36);
        bt37 = findViewById(R.id.button37);
        bt38 = findViewById(R.id.button38);
        bt39 = findViewById(R.id.button39);
        bt40 = findViewById(R.id.button40);
        bt41 = findViewById(R.id.button41);
        bt42 = findViewById(R.id.button42);
        bt43 = findViewById(R.id.button43);
        bt44 = findViewById(R.id.button44);
        bt45 = findViewById(R.id.button45);
        bt46 = findViewById(R.id.button46);
        bt47 = findViewById(R.id.button47);
        bt48 = findViewById(R.id.button48);
        bt49 = findViewById(R.id.button49);
        bt50 = findViewById(R.id.button50);
        bt51 = findViewById(R.id.button51);
        bt52 = findViewById(R.id.button52);
        bt53 = findViewById(R.id.button53);
        bt54 = findViewById(R.id.button54);
        bt55 = findViewById(R.id.button55);
        bt56 = findViewById(R.id.button56);
        bt57 = findViewById(R.id.button57);
        bt58 = findViewById(R.id.button58);
        bt59 = findViewById(R.id.button59);
        bt60 = findViewById(R.id.button60);
        bt61 = findViewById(R.id.button61);
        bt62 = findViewById(R.id.button62);
        bt63 = findViewById(R.id.button63);
        bt64 = findViewById(R.id.button64);
        bt65 = findViewById(R.id.button65);
        bt66 = findViewById(R.id.button66);
        bt67 = findViewById(R.id.button67);
        bt68 = findViewById(R.id.button68);
        bt69 = findViewById(R.id.button69);
        bt70 = findViewById(R.id.button70);
        bt71 = findViewById(R.id.button71);
        bt72 = findViewById(R.id.button72);
        bt73 = findViewById(R.id.button73);
        bt74 = findViewById(R.id.button74);
        bt75 = findViewById(R.id.button75);
        bt76 = findViewById(R.id.button76);
        bt77 = findViewById(R.id.button77);
        bt78 = findViewById(R.id.button78);
        bt79 = findViewById(R.id.button79);
        bt80 = findViewById(R.id.button80);
        bt81 = findViewById(R.id.button81);
        bt82 = findViewById(R.id.button82);
        bt83 = findViewById(R.id.button83);
        bt84 = findViewById(R.id.button84);
        bt85 = findViewById(R.id.button85);
        bt86 = findViewById(R.id.button86);
        bt87 = findViewById(R.id.button87);
        bt88 = findViewById(R.id.button88);
        bt89 = findViewById(R.id.button89);
        bt90 = findViewById(R.id.button90);
        bt91 = findViewById(R.id.button91);
        bt92 = findViewById(R.id.button92);
        bt93 = findViewById(R.id.button93);
        bt94 = findViewById(R.id.button94);
        bt95 = findViewById(R.id.button95);
        bt96 = findViewById(R.id.button96);
        bt97 = findViewById(R.id.button97);
        bt98 = findViewById(R.id.button98);
        bt99 = findViewById(R.id.button99);
        bt100 = findViewById(R.id.button100);
        bt101 = findViewById(R.id.button101);
        bt102 = findViewById(R.id.button102);
        bt103 = findViewById(R.id.button103);
        bt104 = findViewById(R.id.button104);
        bt105 = findViewById(R.id.button105);
        bt106 = findViewById(R.id.button106);
        bt107 = findViewById(R.id.button107);
        bt108 = findViewById(R.id.button108);
        bt109 = findViewById(R.id.button109);
        bt110 = findViewById(R.id.button110);
        bt111 = findViewById(R.id.button111);
        bt112 = findViewById(R.id.button112);
        bt113 = findViewById(R.id.button113);
        bt114 = findViewById(R.id.button114);
        bt115 = findViewById(R.id.button115);
        bt116 = findViewById(R.id.button116);
        bt117 = findViewById(R.id.button117);
        bt118 = findViewById(R.id.button118);
        bt119 = findViewById(R.id.button119);
        bt120 = findViewById(R.id.button120);
        bt121 = findViewById(R.id.button121);
        bt122 = findViewById(R.id.button122);
        bt123 = findViewById(R.id.button123);
        bt124 = findViewById(R.id.button124);
        bt125 = findViewById(R.id.button125);
        bt126 = findViewById(R.id.button126);
        bt127 = findViewById(R.id.button127);
        bt128 = findViewById(R.id.button128);
        bt129 = findViewById(R.id.button129);
        bt130 = findViewById(R.id.button130);
        bt131 = findViewById(R.id.button131);
        bt132 = findViewById(R.id.button132);
        bt133 = findViewById(R.id.button133);
        bt134 = findViewById(R.id.button134);
        bt135 = findViewById(R.id.button135);
        bt136 = findViewById(R.id.button136);
        bt137 = findViewById(R.id.button137);
        bt138 = findViewById(R.id.button138);
        bt139 = findViewById(R.id.button139);
        bt140 = findViewById(R.id.button140);
        bt141 = findViewById(R.id.button141);
        bt142 = findViewById(R.id.button142);
        bt143 = findViewById(R.id.button143);
        bt144 = findViewById(R.id.button144);
        bt145 = findViewById(R.id.button145);
        bt146 = findViewById(R.id.button146);
        bt147 = findViewById(R.id.button147);
        bt148 = findViewById(R.id.button148);
        bt149 = findViewById(R.id.button149);
        bt150 = findViewById(R.id.button150);
        bt151 = findViewById(R.id.button151);
        bt152 = findViewById(R.id.button152);
        bt153 = findViewById(R.id.button153);
        bt154 = findViewById(R.id.button154);
        bt155 = findViewById(R.id.button155);
        bt156 = findViewById(R.id.button156);
        bt157 = findViewById(R.id.button157);
        bt158 = findViewById(R.id.button158);
        bt159 = findViewById(R.id.button159);
        bt160 = findViewById(R.id.button160);
        bt161 = findViewById(R.id.button161);
        bt162 = findViewById(R.id.button162);
        bt163 = findViewById(R.id.button163);
        bt164 = findViewById(R.id.button164);
        bt165 = findViewById(R.id.button165);
        bt166 = findViewById(R.id.button166);
        bt167 = findViewById(R.id.button167);
        bt168 = findViewById(R.id.button168);
        bt169 = findViewById(R.id.button169);
        bt170 = findViewById(R.id.button170);
        bt171 = findViewById(R.id.button171);
        bt172 = findViewById(R.id.button172);
        bt173 = findViewById(R.id.button173);
        bt174 = findViewById(R.id.button174);
        bt175 = findViewById(R.id.button175);
        bt176 = findViewById(R.id.button176);
        bt177 = findViewById(R.id.button177);
        bt178 = findViewById(R.id.button178);
        bt179 = findViewById(R.id.button179);
        bt180 = findViewById(R.id.button180);
        bt181 = findViewById(R.id.button181);
        bt182 = findViewById(R.id.button182);
        bt183 = findViewById(R.id.button183);
        bt184 = findViewById(R.id.button184);
        bt185 = findViewById(R.id.button185);
        bt186 = findViewById(R.id.button186);
        bt187 = findViewById(R.id.button187);
        bt188 = findViewById(R.id.button188);
        bt189 = findViewById(R.id.button189);
        bt190 = findViewById(R.id.button190);
        bt191 = findViewById(R.id.button191);
        bt192 = findViewById(R.id.button192);
        bt193 = findViewById(R.id.button193);
        bt194 = findViewById(R.id.button194);
        bt195 = findViewById(R.id.button195);
        bt196 = findViewById(R.id.button196);
        bt197 = findViewById(R.id.button197);
        bt198 = findViewById(R.id.button198);
        bt199 = findViewById(R.id.button199);
    }


}
