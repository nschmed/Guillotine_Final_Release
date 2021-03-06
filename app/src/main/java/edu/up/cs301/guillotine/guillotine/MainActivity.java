package edu.up.cs301.guillotine.guillotine;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.up.cs301.guillotine.R;

//import edu.up.cs301.guillotine.R;
//import com.example.administrator.guillotine.R;

/**
 * This is the basic view of the game screen, and the activity that supports play.
 *
 * @author Nathan Schmedake
 * @author Muhammed Acar
 * @author Melanie Martinell
 * @author Linnea Bair
 * @version December 2015
 */

public class MainActivity extends Activity {

    //Intent to be used
    Intent intent2;

    //The gui objects
    private TextView promptTextBox;
    private Spinner yourActionCards;
    private Spinner yourNobleCards;
    private TextView humanPlayerScore;
    private FrameLayout popUpActionCard;
    private Button pass;
    private ImageView imageofActionCardChosen;
    private ImageView imageofNobleCardChosen;
    private ArrayList<String> yourActionCardNames;
    private ArrayList<String> yourNobleCardNames;
    private ActionCard actionCardChosen;
    private Noble nobleCardChosen;
    private TextView userName;
    private int numCompPlayers;
    private int difficulty;
    private String name;
    private TextView dayNum;
    private Button lookAtNobles;
    private Button showActionCards;
    private ImageView drawActionCards;

    private int deathRowNum;

    //popUp Action chosen by User
    private FrameLayout popUpUserChosenPlayer;
    private Button player2UserChosen;
    private Button player3UserChosen;
    private Button player4UserChosen;

    private String promptTurnMessage;

    private Button chooseNoblePopUp;

    private ImageView noble1;
    private ImageView noble2;
    private ImageView noble3;
    private ImageView noble4;
    private ImageView noble5;
    private ImageView noble6;
    private ImageView noble7;
    private ImageView noble8;
    private ImageView noble9;
    private ImageView noble10;
    private ImageView noble11;
    private ImageView noble12;

    private TextView player2Score;
    private TextView player3Score;
    private TextView player4Score;
    private TextView player2Title;
    private TextView player3Title;
    private TextView player4Title;
    private TextView player2ScoreTitle;

    private FrameLayout popUpNobleZoomed;
    public static GuillotineState state;
    GameComputerPlayer compPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the game
        //get the variables that the user inputed on the main menu
        intent2 = getIntent();
        int littleArray[] = intent2.getIntArrayExtra("values");
        String nameArray[] = intent2.getStringArrayExtra("name");
        numCompPlayers = littleArray[0];
        difficulty = littleArray[1];
        if (difficulty == 0) {
            compPlayers = new GuillotineComputerPlayerEasy();
        } else if (difficulty == 1) {
            compPlayers = new GuillotineComputerPlayerHard();
        }
        name = nameArray[0];


        //initialize main activity
        promptTextBox = (TextView) findViewById(R.id.PromptTextBox);
        userName = (TextView) findViewById(R.id.userName);

        dayNum = (TextView) findViewById(R.id.dayNum);

        lookAtNobles = (Button) findViewById(R.id.chooseNobleButton);
        showActionCards = (Button) findViewById(R.id.actionCardShowButton);
        drawActionCards = (ImageView) findViewById(R.id.ActionCardsDraw);


        chooseNoblePopUp = (Button) findViewById(R.id.chooseNobleButtonPopUp);
        chooseNoblePopUp.setVisibility(View.INVISIBLE);


        humanPlayerScore = (TextView) findViewById(R.id.humanScore);
        popUpActionCard = (FrameLayout) findViewById(R.id.LayoutForActionCard);
        popUpNobleZoomed = (FrameLayout) findViewById(R.id.FrameLayoutZoomNoble);


        popUpUserChosenPlayer = (FrameLayout) findViewById(R.id.popUpActionUserChoose);
        player2UserChosen = (Button) findViewById(R.id.actionPopUpChoosePlayer2);
        player3UserChosen = (Button) findViewById(R.id.actionPopUpChosenPlayer3);
        player4UserChosen = (Button) findViewById(R.id.actionPopUpchosenPlayer4);


        //set the nobles
        noble1 = (ImageView) findViewById(R.id.noble1);
        noble2 = (ImageView) findViewById(R.id.noble2);
        noble3 = (ImageView) findViewById(R.id.noble3);
        noble4 = (ImageView) findViewById(R.id.noble4);
        noble5 = (ImageView) findViewById(R.id.noble5);
        noble6 = (ImageView) findViewById(R.id.noble6);
        noble7 = (ImageView) findViewById(R.id.noble7);
        noble8 = (ImageView) findViewById(R.id.noble8);
        noble9 = (ImageView) findViewById(R.id.noble9);
        noble10 = (ImageView) findViewById(R.id.noble10);
        noble11 = (ImageView) findViewById(R.id.noble11);
        noble12 = (ImageView) findViewById(R.id.noble12);


        player2Score = (TextView) findViewById(R.id.player2Score);
        player3Score = (TextView) findViewById(R.id.player3Score);
        player4Score = (TextView) findViewById(R.id.player4Score);
        player2Title = (TextView) findViewById(R.id.player2Title);
        player3Title = (TextView) findViewById(R.id.player3Title);
        player4Title = (TextView) findViewById(R.id.player4Title);
        player2ScoreTitle = (TextView) findViewById(R.id.player2NumCards);

        //show only the number of computer players the user decided
        if (numCompPlayers == 1) {
            player4Title.setVisibility(View.INVISIBLE);
            player4Score.setVisibility(View.INVISIBLE);
            player3Title.setVisibility(View.INVISIBLE);
            player3Score.setVisibility(View.INVISIBLE);

        }
        if (numCompPlayers == 2) {
            player4Title.setVisibility(View.INVISIBLE);
            player4Score.setVisibility(View.INVISIBLE);
        }


        imageofActionCardChosen = (ImageView) findViewById(R.id.imageViewForActionCardChosen);
        imageofNobleCardChosen = (ImageView) findViewById(R.id.ImageOfZoomedNoble);
        //makes sure the popup layout is not visible until button is clicked
        popUpActionCard.setVisibility(View.INVISIBLE);
        popUpNobleZoomed.setVisibility(View.INVISIBLE);
        pass = (Button) findViewById(R.id.PassButton);
        //sets the orientation of the screen to landscape
        lockOrientationLandscape(MainActivity.this);
        state = new GuillotineState(numCompPlayers, name, difficulty);


        userName.setText(name + "");


        if (state != null) {
            dayNum.setText(state.getDay() + "");
        }


        //arraylists for noble and action card spinner
        yourActionCardNames = new ArrayList<String>();
        yourNobleCardNames = new ArrayList<String>();

        //titles for both spinners
        yourNobleCardNames.add("---Noble Cards---");
        yourActionCardNames.add("---Action Cards---");


        //initialize the deathrow, the spinners
        setDeathRowLine();
        createActionSpinner();
        createNobleSpinner();
        addListenerOnActionSelected();
        addListenerOnNobleSelected();

        //if the first player is 1, take turn
        if (state.getCurrentPlayer() == 1) {
            compPlayers.takeTurn(state);
        }
        //if the current player is 2, take turn
        if (state.getCurrentPlayer() == 2) {
            compPlayers.takeTurn(state);
        }
        //if the current player is 3, take turn
        if (state.getCurrentPlayer() == 3) {
            compPlayers.takeTurn(state);
        }

        setScores();
        setDeathRowLine();
        promptTextBox.setText(state.getMessage());
    }

    /*
     * Sets the images of the death row nobles
     */
    private void setDeathRowLine() {
        //set the nobles image based on what was initialized in
        //the gamestate
        if (state != null) {

            if (state.deathRow.size() >= 1) {
                noble1.setImageResource(state.deathRow.get(0).getImage());
            } else if (state.deathRow.size() < 1) {

                noble1.setImageResource(0);
                noble1.setClickable(false);

                state.setDay(state.getDay() + 1);


                //if there is no nobles left in the line, decide if game is over
                //or if the day is just over.
                if (state.isGameOver()) {
                    Intent intent = new Intent(this, GameOver.class);
                    int values[] = {state.getHumanPlayerScore(), state.getComputerPlayer1Score(), state.getComputerPlayer2Score(), state.getComputerPlayer3Score()};
                    intent.putExtra("values", values);
                    startActivity(intent);

                }

            }
            //if there is at least 2 nobles in line, set the image
            if (state.deathRow.size() >= 2) {
                noble2.setImageResource(state.deathRow.get(1).getImage());
            }
            //if there is less than 2 nobles in line, set the image to nothing
            //also do not allow the user to click on the non-existent image
            else if (state.deathRow.size() < 2) {
                noble2.setImageResource(0);
                noble2.setClickable(false);
            }
            //same as above
            if (state.deathRow.size() >= 3) {
                noble3.setImageResource(state.deathRow.get(2).getImage());
            } else if (state.deathRow.size() < 3) {
                noble3.setImageResource(0);
                noble3.setClickable(false);
            }
            if (state.deathRow.size() >= 4) {
                noble4.setImageResource(state.deathRow.get(3).getImage());
            } else if (state.deathRow.size() < 4) {
                noble4.setImageResource(0);
                noble4.setClickable(false);
            }
            if (state.deathRow.size() >= 5) {
                noble5.setImageResource(state.deathRow.get(4).getImage());
            } else if (state.deathRow.size() < 5) {
                noble5.setImageResource(0);
                noble5.setClickable(false);
            }
            if (state.deathRow.size() >= 6) {
                noble6.setImageResource(state.deathRow.get(5).getImage());
            } else if (state.deathRow.size() < 6) {
                noble6.setImageResource(0);
                noble6.setClickable(false);
            }
            if (state.deathRow.size() >= 7) {
                noble7.setImageResource(state.deathRow.get(6).getImage());
            } else if (state.deathRow.size() < 7) {
                noble7.setImageResource(0);
                noble7.setClickable(false);
            }
            if (state.deathRow.size() >= 8) {
                noble8.setImageResource(state.deathRow.get(7).getImage());
            } else if (state.deathRow.size() < 8) {
                noble8.setImageResource(0);
                noble8.setClickable(false);
            }
            if (state.deathRow.size() >= 9) {
                noble9.setImageResource(state.deathRow.get(8).getImage());
            } else if (state.deathRow.size() < 9) {
                noble9.setImageResource(0);
                noble9.setClickable(false);
            }
            if (state.deathRow.size() >= 10) {
                noble10.setImageResource(state.deathRow.get(9).getImage());
            } else if (state.deathRow.size() < 10) {
                noble10.setImageResource(0);
                noble10.setClickable(false);
            }
            if (state.deathRow.size() >= 11) {
                noble11.setImageResource(state.deathRow.get(10).getImage());
            } else if (state.deathRow.size() < 11) {
                noble11.setImageResource(0);
                noble11.setClickable(false);
            }
            if (state.deathRow.size() >= 12) {
                noble12.setImageResource(state.deathRow.get(11).getImage());
            } else if (state.deathRow.size() < 12) {
                noble12.setImageResource(0);
                noble12.setClickable(false);
            }
        }

    }

    //set the scores of all of the players on the activity view
    public void setScores() {

        if (state != null) {

            player2Score.setText(state.getComputerPlayer1Score() + "");
            player3Score.setText(state.getComputerPlayer2Score() + "");
            player4Score.setText(state.getComputerPlayer3Score() + "");
            humanPlayerScore.setText(state.getHumanPlayerScore() + "");

        }
    }

    //creates the action spinner based on the initial human hand
    public void createActionSpinner() {

        yourActionCards = (Spinner) findViewById(R.id.spinnerYourActions);
        if (state != null) {
            ArrayList<ActionCard> hand = state.getHumanPlayerHand();
            for (int i = 0; i < hand.size(); i++) {

                yourActionCardNames.add(hand.get(i).getName());
            }
        }
        setAdapterAction();
    }

    //creates the noble spinner
    public void createNobleSpinner() {

        yourNobleCards = (Spinner) findViewById(R.id.spinnerYourNobles);
        if (state != null) {
            ArrayList<Noble> nobles = state.getHumanPlayerNobles();
            for (int i = 0; i < nobles.size(); i++) {
                yourNobleCardNames.add(nobles.get(i).getNobleName());
            }

        }
        setAdapterNoble();
    }

    //set action card spinner
    public void setAdapterAction() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, yourActionCardNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        yourActionCards.setAdapter(adapter);
    }

    public void setAdapterNoble() {
        //set noble card spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                yourNobleCardNames);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        yourNobleCards.setAdapter(adapter2);
    }

    //checks what the user has selected from the spinner
    public void addListenerOnActionSelected() {
        yourNobleCards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                //the spinner title
                if (position == 0) {

                } else {
                    if (state != null) {


                        actionCardChosen = state.getHumanPlayerHand().get(position); //-1
                        imageofActionCardChosen.setImageResource(actionCardChosen.getImage());

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    //checks what the user has selected from the spinner
    public void addListenerOnNobleSelected() {
        yourActionCards.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                //the spinner title
                if (position == 0) {

                } else {
                    if (state != null) {

                        actionCardChosen = state.getHumanPlayerHand().get(position - 1); //-1
                        imageofActionCardChosen.setImageResource(actionCardChosen.getImage());

                    }
                }


            }

            //  }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    //sets the orientation of the screen to landscape
    public static void lockOrientationLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    //noble in deathrow button
    public void nobleOne(View view) {

        //sets the image of the popup layout to that of the noble chosen
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(0).getImage());
            deathRowNum = 0;
        }
        //sets the pop up to be visible and does not allow the user to click on the deathrow
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    //same as above
    public void nobleTwo(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(1).getImage());
            deathRowNum = 1;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleThree(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(2).getImage());
            deathRowNum = 2;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleFour(View view) {
        imageofNobleCardChosen.setImageResource(state.deathRow.get(3).getImage());
        deathRowNum = 3;
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleFive(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(4).getImage());
            deathRowNum = 4;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleSix(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(5).getImage());
            deathRowNum = 5;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleSeven(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(6).getImage());
            deathRowNum = 6;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleEight(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(7).getImage());
            deathRowNum = 7;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleNine(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(8).getImage());
            deathRowNum = 8;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleTen(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(9).getImage());
            deathRowNum = 9;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleEleven(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(10).getImage());
            deathRowNum = 10;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    public void nobleTwelve(View view) {
        if (state != null) {
            imageofNobleCardChosen.setImageResource(state.deathRow.get(11).getImage());
            deathRowNum = 11;
        }
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);

    }

    //chooses the noble that the spinner is currently on
    public void chooseNobleButton(View view) {
        popUpNobleZoomed.setVisibility(view.VISIBLE);
        clickableDeathRow(false);


    }

    //opens up a new window in which the action card should be shown
    //as an image in the imageView: imageOfActionCardChosen
    public void showActionButton(View view) {

        popUpActionCard.setVisibility(view.VISIBLE);
        clickableDeathRow(false);


    }

    public void passButton(View view) {
        //collect first noble in line
        //update score
        //add noble to spinner
        if (state != null) {

            boolean dayDone = false;

            Noble firstNoble = state.deathRow.get(0);

            if (state.deathRow.size() == 1) {
                dayDone = true;
            }
            state.collectNoble(0);

            //checks if day is done. if so, lets the user know
            if (dayDone) {
                Toast.makeText(getApplicationContext(), "Day " + state.getDay() + " has begun.", Toast.LENGTH_SHORT).show();
                dayNum.setText(state.getDay() + "");
            }

            updateNobleSpinner();
            setDeathRowLine();
            state.calculateScores();
            setScores();

            //add action card
            if (state != null) {
                state.getHumanPlayerHand().add(state.actionDeck.get(0));
                state.actionDeck.remove(0);
            }
            updateActionSpinner();


            //set the prompt
            promptTurnMessage = "You have opted to \"pass\" your turn.";
            promptTextBox.setText(promptTurnMessage);

            state.setCurrentPlayer();
            clickableAllButtons(false);
            Toast.makeText(getApplicationContext(), "Your turn is done, wait for other player(s) to take turn(s).", Toast.LENGTH_LONG).show();


            //delays the play by 3 seconds to allow the user to see what they have
            //done to change the state of the game
            Runnable mMyRunnable = new Runnable() {
                @Override
                public void run() {
                    state.resetMessage();
                    computerPlay();
                }
            };

            Handler myHandler = new Handler();
            myHandler.postDelayed(mMyRunnable, 3000);


        }

    }

    //changes all buttons to be clickable or not
    public void clickableAllButtons(boolean click) {

        clickableDeathRow(click);
        pass.setClickable(click);
        lookAtNobles.setClickable(click);
        showActionCards.setClickable(click);
        drawActionCards.setClickable(click);
    }

    //changes the deathrow buttons to be clickable or not
    public void clickableDeathRow(boolean click) {

        noble1.setClickable(click);
        noble2.setClickable(click);
        noble3.setClickable(click);
        noble4.setClickable(click);
        noble5.setClickable(click);
        noble6.setClickable(click);
        noble7.setClickable(click);
        noble8.setClickable(click);
        noble9.setClickable(click);
        noble10.setClickable(click);
        noble11.setClickable(click);
        noble12.setClickable(click);
    }

    //updates the noble spinner after a turn is done
    public void updateNobleSpinner() {
        if (state != null) {

            yourNobleCardNames.clear();
            yourNobleCardNames.add("---Noble Cards---");
            for (int i = 0; i < state.humanPlayerNobles.size(); i++) {
                yourNobleCardNames.add(state.humanPlayerNobles.get(i).getNobleName());
            }
            setAdapterNoble();
        }
    }

    //updates the action spinner after a turn is done
    public void updateActionSpinner() {
        if (state != null) {


            yourActionCardNames.clear();
            yourActionCardNames.add("---Action Cards---");

            for (int i = 0; i < state.humanPlayerHand.size(); i++) {
                yourActionCardNames.add(state.humanPlayerHand.get(i).getName());
            }
            setAdapterAction();
        }
    }

    public void endButton(View view) {

        finish();

    }

    public void helpButton(View view) {

        startActivity(new Intent(MainActivity.this, RulesScreen.class));

    }

    //images that were turned button on the left hand side
    //allows the user to get a new action card if prompted
    //used only for testing! DELETE THIS!!!
    public void actionCardDrawButton(View view) {

       /* if (state != null) {
            state.getHumanPlayerHand().add(state.actionDeck.get(0));
            state.actionDeck.remove(0);
        }
        updateActionSpinner();*/

    }

    //if player2 was chosen from the pop up on certain action cards
    public void player2UserChosen(View view) {
        //sets the user input to 2 for action card
        state.setIntPopUpRecievedInfo(2);
        //makes the pop up invisible
        popUpUserChosenPlayer.setVisibility(View.INVISIBLE);
        //goes through the ActionCard class again- actually does the action
        //this time
        state = actionCardChosen.humanAction(state);
        //finishes up the action
        actionChosenPart2();

    }

    //if player3 was chosen from the pop up on certain action cards
    //same as for player2
    public void player3UserChosen(View view) {
        state.setIntPopUpRecievedInfo(3);
        popUpUserChosenPlayer.setVisibility(View.INVISIBLE);
        state = actionCardChosen.humanAction(state);
        actionChosenPart2();
    }

    //if player4 was chosen from the pop up on certain action cards
    public void player4UserChosen(View view) {
        state.setIntPopUpRecievedInfo(4);
        popUpUserChosenPlayer.setVisibility(View.INVISIBLE);
        state = actionCardChosen.humanAction(state);
        actionChosenPart2();
    }


    //chooses the action card displayed on the pop up screen
    //to play
    public void chooseActionCardToPlay(View view) {

        if (actionCardChosen != null && imageofActionCardChosen != null) {

            //Use actionCardChosen to decide what action to do
            String actionChosen = (actionCardChosen.getName());
            state.setActionCardChosen(actionCardChosen);
            state.resetMessage();
            //promptTextBox.setText("You have chosen to play the "+actionChosen+" card.");
            promptTurnMessage = "You have chosen to play the \"" + actionChosen + "\" card.";
            promptTextBox.setText(promptTurnMessage);
            state = actionCardChosen.humanAction(state);

            //if a pop up is needed for an action card that requires the user
            //to choose a player
            if (state.getNeedPopUp() == true && state.getPopUpType() == 0) {

                //set pop up to visible
                //buttons = number of computer players
                player3UserChosen.setVisibility(View.INVISIBLE);
                player4UserChosen.setVisibility(View.INVISIBLE);


                popUpUserChosenPlayer.setVisibility(View.VISIBLE);

                player2UserChosen.setVisibility(View.VISIBLE);

                if (state.getNumPlayers() >= 3) {
                    player3UserChosen.setVisibility(View.VISIBLE);

                }
                if (state.getNumPlayers() == 4) {
                    player4UserChosen.setVisibility(View.VISIBLE);
                }


            }
            //if a user is needed to choose a card for an action card
            else if (state.getNeedPopUp() == true && state.getPopUpType() == 1) {

                state.addToMessage("Please choose card from DeathRow to play for the action card you have chosen.");
                popUpActionCard.setVisibility(View.INVISIBLE);
                clickableDeathRow(true);
                setDeathRowLine();

                chooseNoblePopUp.setVisibility(View.VISIBLE);
                promptTextBox.setText(state.getMessage());
            } else {
                actionChosenPart2();
            }
        }

    }

    //ends the human action
    public void actionChosenPart2() {
        setDeathRowLine();
        updateActionSpinner();
        updateNobleSpinner();


        boolean dayDone = false;


        if (state.deathRow.size() == 1) {
            dayDone = true;
        }
        Noble firstNoble = state.deathRow.get(0);
        state.collectNoble(0);
        //checks if the day is done
        if (dayDone) {
            Toast.makeText(getApplicationContext(), "Day " + state.getDay() + " has begun.", Toast.LENGTH_LONG).show();
            dayNum.setText(state.getDay() + "");
            updateActionSpinner();
        }

        setDeathRowLine();
        updateNobleSpinner();
        state.calculateScores();
        setScores();

        //removes the action card from the action spinner
        int removeAction = -1;
        for (int i = 0; i < yourActionCardNames.size(); i++) {
            if (yourActionCardNames.get(i).equals(state.getActionChosen().getName())) {
                removeAction = i;
                break;
            }
        }
        if (removeAction != -1) {
            yourActionCardNames.remove(removeAction);
        }

        //removes the action card that was played from the user's hand
        int remove = -1;
        for (int i = 0; i < state.humanPlayerHand.size(); i++) {
            if (state.humanPlayerHand.get(i).getName().equals(state.getActionChosen().getName())) {
                remove = i;
                break;
            }
        }
        if (remove != -1) {
            state.actionDiscard.add(state.humanPlayerHand.get(remove));
            state.humanPlayerHand.remove(remove);
        }
        //adds a new action card to the user's hand
        state.humanPlayerHand.add(state.actionDeck.get(0));
        state.actionDeck.remove(0);
        //updates the action spinner
        updateActionSpinner();
        popUpActionCard.setVisibility(View.INVISIBLE);

        state.setCurrentPlayer();
        clickableAllButtons(false);
        Toast.makeText(getApplicationContext(), "Your turn is done, wait for other player(s) to take turn(s).", Toast.LENGTH_LONG).show();

        //if the game is not done...
        if (state.getDay() < 4) {
            Runnable mMyRunnable = new Runnable() {
                @Override
                public void run() {
                    computerPlay();
                }
            };


            //delays the play for 5 seconds
            Handler myHandler = new Handler();
            myHandler.postDelayed(mMyRunnable, 5000);
        }

        actionCardChosen = null;
        imageofActionCardChosen.setImageResource(0);
    }



    public void computerPlay() {
        //one or more comp players

        boolean dayDone = false;

        if (state.getNumPlayers() >= 2) {

            //takes the turn of the computer player 2
            compPlayers.takeTurn(state);

            if (state.deathRow.size() == 0) {
                dayDone = true;
            }
            //checks if day is done
            if (dayDone) {
                Toast.makeText(getApplicationContext(), "Day " + state.getDay() + " has begun.", Toast.LENGTH_LONG).show();
                dayNum.setText(state.getDay() + "");
            }
            //resets everything
            updateNobleSpinner();
            updateActionSpinner();
            setDeathRowLine();
            state.calculateScores();
            setScores();

            promptTextBox.setText(state.getMessage());

            //if computer players are done, let user know and reset everything
            if (state.getNumPlayers() == 2 && state.getDay() <= 3) {
                clickableAllButtons(true);
                setDeathRowLine();
                Toast.makeText(getApplicationContext(), "Your Turn.", Toast.LENGTH_SHORT).show();
            }

        }

        //two or more comp players
        if (state.getNumPlayers() >= 3 && state.getDay() <= 3) {

            //delays by 5 seconds
            Runnable mMyRunnable3 = new Runnable() {
                @Override
                public void run() {

                    //does the same as player2
                    compPlayer2();

                    promptTextBox.setText(state.getMessage());

                    if (state.getNumPlayers() == 3 && state.getDay() <= 3) {

                        clickableAllButtons(true);
                        setDeathRowLine();
                        Toast.makeText(getApplicationContext(), "Your Turn.", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            Handler myHandler3 = new Handler();
            myHandler3.postDelayed(mMyRunnable3, 5000);

        }

    }

    public void compPlayer2() {
        boolean dayDone = false;

        compPlayers.takeTurn(state);
        if (state.deathRow.size() == 0) {
            dayDone = true;
        }

        if (dayDone) {
            Toast.makeText(getApplicationContext(), "Day " + state.getDay() + " has begun.", Toast.LENGTH_LONG).show();

            dayNum.setText(state.getDay() + "");
        }
        updateNobleSpinner();
        updateActionSpinner();
        setDeathRowLine();
        state.calculateScores();
        setScores();

        //three comp players
        if (state.getNumPlayers() == 4 && state.getDay() <= 3) {

            //delays by 5 seconds
            Runnable mMyRunnable4 = new Runnable() {
                @Override
                public void run() {

                    //does the same as other computer players
                    compPlayer3();

                    promptTextBox.setText(state.getMessage());

                    if (state.getNumPlayers() == 4) {

                        clickableAllButtons(true);
                        setDeathRowLine();
                        Toast.makeText(getApplicationContext(), "Your Turn.", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            Handler myHandler4 = new Handler();
            myHandler4.postDelayed(mMyRunnable4, 5000);
        }
    }

    public void compPlayer3() {

        compPlayers.takeTurn(state);

        boolean dayDone = false;

        if (state.deathRow.size() == 1) {
            dayDone = true;
        }

        if (dayDone) {
            Toast.makeText(getApplicationContext(), "Day " + state.getDay() + " has begun.", Toast.LENGTH_LONG).show();

            dayNum.setText(state.getDay() + "");
        }
        updateNobleSpinner();
        updateActionSpinner();
        setDeathRowLine();
        state.calculateScores();
        setScores();
        promptTextBox.setText(state.getMessage());

    }

    //chooses the noble card displayed on the pop up screen
    //to be played
    public void chooseNobleFromZoomedNoble(View view) {

        clickableDeathRow(false);
        popUpNobleZoomed.setVisibility(View.INVISIBLE);
        state.resetMessage();
        state.setIntPopUpRecievedInfo(deathRowNum);
        state = actionCardChosen.humanAction(state);
        chooseNoblePopUp.setVisibility(View.INVISIBLE);
        actionChosenPart2();

    }

    //pop up will disappear
    public void goBackToPlay(View view) {
        popUpActionCard.setVisibility(view.INVISIBLE);
        clickableDeathRow(true);


    }

    //exits the zoomed in noble pop up
    public void goBackFromZoomedNoble(View view) {
        popUpNobleZoomed.setVisibility(view.INVISIBLE);
        clickableDeathRow(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



