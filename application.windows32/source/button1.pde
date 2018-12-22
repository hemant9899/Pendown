class buttons1 {
  PFont butfont;
  int select=1, mouse=0, vari=1;
  float xx, yy, ww, hh;
  String slable, dlable;
  void buttons1(float x, float y, float w, float h, String lable1, String lable2) {
    
    
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
  void display() {

    textFont(butfont);
    textSize(hh*0.9);
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
      text(slable, xx+ww/2, yy+0.8*hh);
    else
      text(dlable, xx+ww/2, yy+0.8*hh);
    popStyle();
    //println(vari);
  }
  int getvari() {
    return vari;
  }
}

