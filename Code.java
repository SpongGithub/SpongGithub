import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;
import android.app.Activity;
Activity act;
Context cnt;
SharedPreferences sp;
SharedPreferences.Editor editor;
PImage skin_1;
PImage char_r;
PImage play;
PImage try_b;
PImage st;
int myScore;
String myScoreFile;
int hiScore;
String hiScoreFile;
int spieler_x;
int spieler_y;
int spieler_w;
int spieler_h; 
float ball_x;
float ball_y;
float ball_ge_x;
float ball_ge_y;
int ball_d;
int seite = 0; //0 = start, 1 = play, 2 = end 
void setup() {
  orientation(PORTRAIT);
  play = loadImage("play.png");
  try_b = loadImage("try_b.png");
  char_r = loadImage("char_1.png");
  skin_1 = loadImage("skin_1.png");
  st = loadImage("st2.png");
  frameRate(120);
  PFont font = createFont("SansSerif", 24 * displayDensity); 
  textFont(font); 
  fullScreen(); 
  rectMode(CENTER); 
  spieler_w = width/15; 
  spieler_h = height/5; 
  ball_d = width/12;
  act = this.getActivity();
  cnt = act.getApplicationContext();
  sp = PreferenceManager.getDefaultSharedPreferences(cnt);
  editor = sp.edit();
  hiScoreFile = "hiScores";
  hiScore = loadScore(hiScoreFile);
  myScoreFile = "Scores";
  myScore = loadScore(myScoreFile);
} 
void draw(){
  background(0);
  if (seite == 0) {
    showStart();
    if (mousePressed) { 
      if (mouseX > width/2 - width/3/2 && mouseX < width/2 + width/3/2 && mouseY > height/1.5 - height/12/2 && mouseY < height/1.5 + height/12/2) { 
        reset(); 
        delay(300);
        seite = 1;
      }
    }
  } 
  if (seite == 1) { 
    showGame(); 
    berechnePlayerY(); 
    ball_x = ball_x + ball_ge_x; 
    ball_y = ball_y + ball_ge_y; 
    berechneBall();
  } 
  if (seite == 2) { 
    showEnd();
    if (mousePressed) { 
      if (mouseX > width/2 - width/3/2 && mouseX < width/2 + width/3/2 && mouseY > height/1.5 - height/12/2 && mouseY < height/1.5 + height/12/2) { 
        reset();
        delay(300);
        seite = 0;
      }
    }
  }
} 
void reset() {
  myScore = 0;
  spieler_x = width/10; 
  spieler_y = height/2; 
  ball_x = width/2; 
  ball_y = height/2; 
  ball_ge_x = -4; 
  ball_ge_y = 0;
} 
void showGame(){
  fill(255);
  image(char_r, spieler_x - spieler_w/2 , spieler_y - spieler_h/2);
  char_r.resize(spieler_w, spieler_h);
  //rect(spieler_x, spieler_y, spieler_w, spieler_h);
  image(skin_1, ball_x - ball_d/2 , ball_y - ball_d/2);
  skin_1.resize(ball_d, ball_d);
  //ellipse(ball_x, ball_y, ball_d, ball_d);
  fill(255);
  textSize(20 * displayDensity); 
  text("Score: " + myScore, width/2, height/14);
}
void showStart(){
  fill(255); 
  image(play, width/2 - (width/3)/2 , height/1.5 - (height/12)/2);
  play.resize(width/3, height/12);
  textAlign(CENTER); 
  textSize(40 * displayDensity); 
  fill(255); 
  text("SPONG\n\nBETA", width/2, height/5); 
  textSize(20 * displayDensity); 
  fill(255); 
  text("Dein Highscore: " + hiScore + " Punkte", width/2, height/14);
  image(st, width/2.4 - (width/6)/2.4, height/2 - (height/12)/2);
  st.resize(width/6, height/12);
}
void showEnd() {
  fill(255); 
  image(try_b, width/2 - (width/3)/2 , height/1.5 - (height/12)/2);
  try_b.resize(width/3, height/12);
  textAlign(CENTER); 
  textSize(40 * displayDensity); 
  fill(255);
  text("VERLOREN", width/2, height/5);
}
void berechnePlayerY() {
  if (mousePressed) {
    if (mouseY > height / 2) {
      if (spieler_y < height - spieler_h/2) { 
        spieler_y = spieler_y + 10;
      }
    }
    if (mouseY < height / 2) { 
      if (spieler_y > spieler_h/2) { 
        spieler_y = spieler_y - 10;
      }
    }
  }
}
void berechneBall() {
  if (ball_x < spieler_x + spieler_w) { 
    if (ball_y < (spieler_y + spieler_h/2) && ball_y > (spieler_y - spieler_h/2)) { 
      ball_ge_x = (-ball_ge_x) + 1 * 1; 
      ball_ge_y = ball_ge_y - (spieler_y - ball_y) * 0.1;
      myScore++;
      if(myScore > hiScore){
        hiScore++;
        saveScore(hiScore,hiScoreFile);
        }
    } else {
      seite = 2;
    }
  }
  if (ball_y > height-ball_d/2 || ball_y < ball_d/2) { 
    ball_ge_y = -ball_ge_y;
  }
  if (ball_x > width-ball_d/2) { 
    ball_ge_x = -ball_ge_x;
  }
}
void saveScore(int hiScore, String hiScoreFile) {
  //editor.clear();
  editor.putInt(hiScoreFile, hiScore);
  editor.commit();
}
int loadScore(String name) {
  int getScore = sp.getInt(name, 0);
  return getScore;
}
void onPause() {
  super.onPause();
  saveScore(hiScore, hiScoreFile);
}
