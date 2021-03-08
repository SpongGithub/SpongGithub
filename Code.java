float spieler_x;
float spieler_y;
float ball_x;
float ball_y;
float ball_ge_x;
float ball_ge_y;
int score;
int seite;

void setup() {
  PFont font = createFont("SansSerif", 24 * displayDensity);
  textFont(font);
  spieler_x = 70;
  spieler_y = height/2;
  ball_x = width/2;
  ball_y = height/2;
  ball_ge_x = -4 * displayDensity;
  ball_ge_y = 0 * displayDensity;
  score = 0;
  seite = 0;
  fullScreen();
  rectMode(CENTER);
}



void draw() {
  background(0);
  if (seite==0){
    fill(255);
    rect(width/2,height/1.5,width/3,height/12, 7 * displayDensity);
    fill(#9E9E9E);
    text("Start", width/2.3,height/1.48);
    text("Spong", width/2.5,height/5);
    if(mousePressed){
      if(mouseX>width/2 && mouseX <width/2+width/3 && mouseY>height/1.5 && mouseY <height/1.5+height/12){
        seite = 1;
        }
      }
      fill(#FFFFFF);
    }
  if (seite==1) {
    rect(spieler_x, spieler_y, width/15, height/5, 7 * displayDensity);
    ellipse(ball_x, ball_y, width/12, height/24);
    if (mousePressed) {
      if (mouseY > height / 2) {
        if (spieler_y < height - height/10) {
          spieler_y = spieler_y + 10 * displayDensity;
        }
      }
      if (mouseY < height / 2) {
        if (spieler_y > height/10) {     
          spieler_y = spieler_y - 10 * displayDensity;
        }
      }
    }
    ball_x = ball_x + ball_ge_x;
    ball_y = ball_y + ball_ge_y;

    if (ball_x < 140) {
      if (ball_y < (spieler_y + height/5*0.66) && ball_y > (spieler_y - height/5 *0.66)) {
        ball_ge_x = (-ball_ge_x) + 0.4 * displayDensity;
        ball_ge_y = ball_ge_y - (spieler_y - ball_y) * 0.1;
        score = score + 1;
      } else {
        ball_x = width/2;
        ball_y = height/2; 
        ball_ge_x = -4 * displayDensity;
        ball_ge_y  = 0 * displayDensity;
        seite = 2;
      }
    }
    if (ball_y > height-height/24 || ball_y < height/24) {
      ball_ge_y = -ball_ge_y;
    }
    if (ball_x > width-90) { 
      ball_ge_x = -ball_ge_x;
    }
    text("Score: " + score, 500, 60);
  }
  if(seite==2){
    fill(255);
    rect(width/2,height/2,width/3,height/12, 7 * displayDensity);
    fill(#9E9E9E);
    text("Score: " + score, 500, 60);
    text("Verloren", width/2.3, height/2);
    if(mousePressed){
      if(mouseX>width/2 && mouseX <width/2+width/3 && mouseY>height/2 && mouseY <height/2+height/12){
        score = 0;
        seite = 0;
        spieler_x = 70;
        spieler_y = height/2;
      }
    }
  }
}
