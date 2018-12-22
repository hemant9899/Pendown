float xpos[][];
float ypos[][];
int steps=0;
float prilen=400;
float priangle, secangle, angle, distlen;
float mx, my, prix, priy, secx, secy;
buttons1 opt1, opt2, opt3;
Arduino arduino;
import de.looksgood.ani.*;
import processing.serial.*;
import cc.arduino.*;
void setup() {
  size(600, 700);
  println(Arduino.list());
  Ani.init(this);
  xpos = new float [width*height][2];
  ypos = new float [width*height][2];
  strokeWeight(3);
  arduino = new Arduino(this, "COM11", 57600);
  arduino.pinMode(8, Arduino.SERVO);
  arduino.pinMode(9, Arduino.SERVO);
  arduino.pinMode(10, Arduino.SERVO);
  //strokeCap(ROUND);
}
void draw() {
  background(220);
  //mx=mouseX;
  //my=mouseY;
  Ani.to(this, .25, "mx", mouseX);
  Ani.to(this, .25, "my", mouseY);
  
  //println(frameRate);
  display();
  arm();
  arduino.servoWrite(8, (int)degrees(priangle));
  
  
  //delay(10);
}

void arm(){
  strokeWeight(3);
  pushMatrix();
  angle=atan2((height-my),( mx));
  stroke(0,200,0);
  smooth();
  line(0,height,mx,my);
  popMatrix();
  distlen=dist( 0, height, mx, my);
  secangle=2*asin(distlen/(2*prilen));
  priangle=PI/2+angle-secangle/2;
  arduino.servoWrite(9, 180-(int)degrees(secangle));
  println((int)degrees(priangle));
  secangle=secangle+priangle-PI;
  fill(0, 0, 255);
  text("r="+(int)distlen+"@="+(int)degrees(angle),mx,my);
  priline(0,height,priangle);
  secline(prix,priy,secangle);
}

void priline(float ix,float iy,float theta){
  prix=ix+prilen*cos(theta);
  priy=iy+prilen*sin(-theta);
  stroke(200,0,0);
  smooth();
  line(ix,iy,prix,priy);
}

void secline(float ix,float iy,float theta){
  //theta=theta-PI/2;
  prix=ix+prilen*cos(theta);
  priy=iy+prilen*sin(-theta);
  stroke(200,0,0);
  smooth();
  line(ix,iy,prix,priy);
}





void display() {
  for (int i=0;i<steps;i++) {
    strokeWeight(2);
    stroke(0,0,200);
    line(xpos[i][1], ypos[i][1], xpos[i][0], ypos[i][0]);
  }
}

void mousePressed() {
  xpos[steps][0]=mouseX;
  ypos[steps][0]=mouseY;
  xpos[steps][1]=pmouseX;
  ypos[steps][1]=pmouseY;
  steps++;
  arduino.servoWrite(10, 0);
}
void mouseReleased(){
  arduino.servoWrite(10, 40);
}
void mouseDragged() {
  xpos[steps][0]=mouseX;
  ypos[steps][0]=mouseY;
  xpos[steps][1]=pmouseX;
  ypos[steps][1]=pmouseY;
  steps++;
}

