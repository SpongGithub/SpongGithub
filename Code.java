float spieler_x;
float spieler_y;
int spieler_w;
int spieler_h;

float ball_x;
float ball_y;
float ball_ge_x;
float ball_ge_y;
int ball_d;

int score;

int Hi_score;
int seite = 0;  //0 = start , 1 = play , 2 = end

void setup() {
  
  //am pc gibt es kein font ... einfach die // weg machen
  //PFont font = createFont("SansSerif", 24 * displayDensity);
  //textFont(font);


  //einfach vor size // machen und vor fullscreen die // weg machen
  size(400, 800);
  //fullScreen();
  rectMode(CENTER);
  
  
  String[] data=loadStrings("lines.txt");
  Hi_score = int(data[0]);

  spieler_w = width/15;
  spieler_h = height/5;
  ball_d = width/12;

  reset();
}

void draw() {
  background(255);
  if (seite == 0) {
    showStart();

    if (mousePressed) {
      if (mouseX > width/2 - width/3/2 && mouseX < width/2 + width/3/2 && mouseY > height/1.5 - height/12/2 && mouseY < height/1.5 + height/12/2) {
        reset();
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
        seite = 1;
      }
    }
  }
  
}


void reset() {
  score = 0;
  spieler_x = width/10;
  spieler_y = height/2;
  ball_x = width/2;
  ball_y = height/2;
  ball_ge_x = -4;
  ball_ge_y = 0;
}

void showGame() {
  fill(255);
  rect(spieler_x, spieler_y, spieler_w, spieler_h, 7);
  ellipse(ball_x, ball_y, ball_d, ball_d);
  line(0, height/2, width, height/2);
  fill(0);
  text(score + "/"+ Hi_score,width/2,height/10);
}

void showStart() {
  fill(255);
  rect(width/2, height/1.5, width/3, height/12, 7);
  textAlign(CENTER);
  textSize(40);
  fill(0);
  text("SPONG", width/2, height/5);
  textSize(20);
  fill(0);
  text("START", width/2, height/1.49);
}

void showEnd(){
  fill(255);
  rect(width/2, height/1.5, width/3, height/12, 7);
  textAlign(CENTER);
  textSize(40);
  fill(0);
  text("VERLOREN", width/2, height/5);
  textSize(20);
  fill(0);
  text("TRY AGAIN", width/2, height/1.49);
}

void berechnePlayerY(){
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

void berechneBall(){
  if (ball_x < spieler_x + spieler_w) {
      if (ball_y < (spieler_y + spieler_h/2) && ball_y > (spieler_y - spieler_h/2)) {
        ball_ge_x = (-ball_ge_x) + 0.4 * 1;
        ball_ge_y = ball_ge_y - (spieler_y - ball_y) * 0.1;
        score = score + 1;
        if(score > Hi_score){
          String[] data=loadStrings("lines.txt");
          String[] newTxt = {str(int(data[data.length -1]) + 1)}; 
          saveStrings("lines.txt", newTxt);
          Hi_score ++;
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
