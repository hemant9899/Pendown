import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import de.looksgood.ani.*; 
import processing.serial.*; 
import cc.arduino.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Pendown extends PApplet {

float xpos[][];
float ypos[][];
int steps=0;
float prilen=400;
float priangle, secangle, angle, distlen;
float mx, my, prix, priy, secx, secy;
buttons1 opt1, opt2, opt3;
Arduino arduino;



public void setup() {
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
public void draw() {
  background(220);
  //mx=mouseX;
  //my=mouseY;
  Ani.to(this, .25f, "mx", mouseX);
  Ani.to(this, .25f, "my", mouseY);
  
  //println(frameRate);
  display();
  arm();
  arduino.servoWrite(8, (int)degrees(priangle));
  
  
  //delay(10);
}

public void arm(){
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

public void priline(float ix,float iy,float theta){
  prix=ix+prilen*cos(theta);
  priy=iy+prilen*sin(-theta);
  stroke(200,0,0);
  smooth();
  line(ix,iy,prix,priy);
}

public void secline(float ix,float iy,float theta){
  //theta=theta-PI/2;
  prix=ix+prilen*cos(theta);
  priy=iy+prilen*sin(-theta);
  stroke(200,0,0);
  smooth();
  line(ix,iy,prix,priy);
}





public void display() {
  for (int i=0;i<steps;i++) {
    strokeWeight(2);
    stroke(0,0,200);
    line(xpos[i][1], ypos[i][1], xpos[i][0], ypos[i][0]);
  }
}

public void mousePressed() {
  xpos[steps][0]=mouseX;
  ypos[steps][0]=mouseY;
  xpos[steps][1]=pmouseX;
  ypos[steps][1]=pmouseY;
  steps++;
  arduino.servoWrite(10, 0);
}
public void mouseReleased(){
  arduino.servoWrite(10, 40);
}
public void mouseDragged() {
  xpos[steps][0]=mouseX;
  ypos[steps][0]=mouseY;
  xpos[steps][1]=pmouseX;
  ypos[steps][1]=pmouseY;
  steps++;
}

class buttons1 {
  PFont butfont;
  int select=1, mouse=0, vari=1;
  float xx, yy, ww, hh;
  String slable, dlable;
  public void buttons1(float x, float y, float w, float h, String lable1, String lable2) {
    
    
    x=x-w/2;
    hh=h;
    ww=w;
    xx=x;
    yy=y;
    dlable=lable2;
    slable=lable1;
    butfont  = loadFont("AgencyFB-Reg-30.vlw");
    //fill(#00FF46);
  }
  public void display() {

    textFont(butfont);
    textSize(hh*0.9f);
    textAlign(CENTER);

    if (!mousePressed) {
      if (select==0) {
        fill(255, 0, 0);//red
      }
      else {
        fill(0, 255, 0);//green
      }
      mouse=0;
    }



    if (mousePressed&&mouse==0) {
      if (mouseX>=xx&&mouseX<=xx+ww) {
        if (mouseY>=yy&&mouseY<=yy+hh) {
          if (select==0) {
            fill(0, 255, 0);//green
            select=1;
            vari=1;
          }
          else {
            fill(255, 0, 0);//red  deselect
            select=0;
            vari=0;
          }
        }
      }
      mouse=1;
    }
    stroke(100);
    strokeWeight(5);
    strokeCap(ROUND);
    rect(xx, yy, ww, hh, 50);
    pushStyle();
    fill(0, 0, 255);//blue
    if (select==1)
      text(slable, xx+ww/2, yy+0.8f*hh);
    else
      text(dlable, xx+ww/2, yy+0.8f*hh);
    popStyle();
    //println(vari);
  }
  public int getvari() {
    return vari;
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Pendown" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
