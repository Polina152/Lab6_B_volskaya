import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BouncingBall{

    private Field field;
    private Thread thisThread;
    private String name;

    private int radius;
    private double speedX;
    private double speedY;
    private  double speed;
    private Color color;
    private double x;
    private double y;
    private boolean isOnBorder=false;
    private boolean isStopped=false;

    public BouncingBall(Field field) {
        int maxRadius=40;
        int minRadius=3;

        name="ball "+(int)(Math.random()*10);
        int maxSpeed=15;

        this.field = field;

        radius = new Double(Math.random()*(maxRadius - minRadius)).intValue()
                + minRadius;


         speed = new Double(Math.round(5*maxSpeed / radius)).intValue();
        if (speed>maxSpeed) {
            speed = maxSpeed;}


        double angle = Math.random()*2*Math.PI;

        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);

        color = new Color((float)Math.random(), (float)Math.random(),
                (float)Math.random());


        x = Math.random()*(field.getSize().getWidth() - 2*radius) + radius;
        y = Math.random()*(field.getSize().getHeight() - 2*radius) + radius;


         thisThread= new Thread(myBalls);

        thisThread.start();
    }
    public BouncingBall(Field field,int radius) {

        name="Big Billy";
        int maxSpeed=15;

        this.field = field;

        this.radius = radius;

        speed = new Double(Math.round(5*maxSpeed / radius)).intValue();
        if (speed>maxSpeed) {
            speed = maxSpeed;}

        double angle = Math.random()*2*Math.PI;

        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);

        color = new Color((float)Math.random(), (float)Math.random(),
                (float)Math.random());

        x= Math.random()*(field.getSize().getWidth() - 2*radius) + radius;
        y = Math.random()*(field.getSize().getHeight() - 2*radius) + radius;


        thisThread= new Thread(myBalls);

        thisThread.start();
    }

    public String GetName(){
        return this.name;
    }


    public void paint(Graphics2D canvas) {
        canvas.setColor(color);
        canvas.setPaint(color);
        Ellipse2D.Double ball = new Ellipse2D.Double(x - radius, y - radius,
                2*radius, 2*radius);
            canvas.draw(ball);
            if(name.equals("Big Billy")){

            }else{
            canvas.fill(ball);
            }
            canvas.setColor(Color.BLACK);
            canvas.drawString(name,(int)Math.round(x)+radius,(int)Math.round(y));



    }
    Runnable myBalls=new Runnable() {
        @Override
        public void run() {
            RunMethod();
        }
    };

    private void SnowBallMethod(){
            if(radius<=50){
                int path=(int)Math.round(Math.sqrt(speedX*speedX+speedY*speedY));
                radius+=(int)Math.round((path* field.GetSnowBallY())/ field.GetSnowBallX());
            }
    }

    private void RunMethod(){
        try {
            while (true) {
               if(field.GetIsGrossFeeder()) {
                  GrossFeederRunMethod();
                }else{
                    RunMethodLogic();
                }

                if(16-speed+field.GetTimeMachine()>0) {
                    Thread.sleep((int) (Math.round(16 - speed + field.GetTimeMachine())));
                }else{
                    Thread.sleep(1);
                }
            }
        } catch (InterruptedException ex) {

        }
    }

    private void GrossFeederRunMethod(){
        System.out.println("fuck");
        double billyX = field.FindBigBilly().x;
        double billyY = field.FindBigBilly().y;
        double billySpeedX=field.FindBigBilly().speedX/(16-field.FindBigBilly().speed);
        double billySpeedY=field.FindBigBilly().speedY/(16-field.FindBigBilly().speed);
        int billyRadius = field.FindBigBilly().radius;
        double newSpeedX=speedX/(16-speed);
        double newSpeedY=speedY/(16-speed);
        if (Math.sqrt(((x-billyX)*(x-billyX))+((y-billyY)*(y-billyY)))<billyRadius-radius) {
            try {
                field.canMove();
            }catch (InterruptedException e){

            }
            int left=(int)Math.round(Math.sqrt((x+newSpeedX-billyX-billySpeedX)*(x+newSpeedX-billyX-billySpeedX))+
                    ((y+newSpeedY-billyY-billySpeedY)*(y+newSpeedY-billyY-billySpeedY)));
            System.out.println(left+" ? "+(billyRadius-radius));
            if(left>=(billyRadius-radius)
                    &&x!=billyX&&y!=billyY)  {
                double speedy=Math.sqrt(newSpeedX*newSpeedX+newSpeedY*newSpeedY);
                double speedXinBilly=newSpeedX-billySpeedX;
                double speedYinBilly=newSpeedY-billySpeedY;
                double cosAlpha=(speedXinBilly)/(speedy);
                double sinAlpha=(speedYinBilly)/(speedy);
                double cosTeta=(x-billyX)/(billyRadius-radius);
                double sinTeta=(y-billyY)/(billyRadius-radius);
                System.out.println("cosAlpha "+cosAlpha+ "  sinAlpha "+sinAlpha+"  cosTeta  "+cosTeta+"  sinTeta  "+sinTeta);
                double speedTang=speedy*(sinTeta*cosAlpha-sinAlpha*cosTeta);
                double speedNorm=speedy*(cosTeta*cosAlpha+sinTeta*sinAlpha);
                speedNorm=-speedNorm;
                if(cosTeta*cosAlpha+sinAlpha*sinTeta>0){
                    speedYinBilly=-speedNorm*cosTeta+speedTang*sinTeta;
                    speedXinBilly=speedNorm*sinTeta-speedTang*cosTeta;
                    newSpeedX=speedXinBilly+billySpeedX;
                    newSpeedY=speedYinBilly+billySpeedY;
                }
                speedX=newSpeedX*(16-speed);
                speedY=newSpeedY*(16-speed);

            }
            else {
                if (field.GetIsCharisma()) {
                    if (field.GetX() >= billyX + billyRadius || field.GetY() >= billyY + billyRadius) {

                    } else {
                        x = field.GetX();
                        y = field.GetY();
                    }
                } else {
                    if (field.GetIsTeam()) {
                        WeAreTeamMethod();
                    } else {

                        if (field.GetIsSnowBall()) {
                            SnowBallMethod();
                        }
                        x += speedX;
                        y += speedY;

                    }
                }
            }
        } else {
            RunMethodLogic();
        }
    }


    private void RunMethodLogic(){
        if(speed>=-200){
            if(field.GetIsFriction()){
                speed-= field.GetFriction();
            }
        }

        try {
            field.canMove();
        }catch (InterruptedException e){

        }

        if(StopChecker()){

        }else{
        if (x + speedX <= radius) {
            speedX = -speedX;
            x = radius;
            isOnBorder=true;
        } else
        if (x + speedX >= field.getWidth() - radius) {
            speedX = -speedX;
            x = new Double(field.getWidth() - radius).intValue();
            isOnBorder=true;
        } else
        if (y + speedY <= radius) {
            speedY = -speedY;
            y = radius;
            isOnBorder=true;
        } else
        if (y + speedY >= field.getHeight() - radius) {
            speedY = -speedY;
            y = new Double(field.getHeight() - radius).intValue();
            isOnBorder=true;
        } else {
            if(field.GetIsCharisma()){
                x= field.GetX();
                y= field.GetY();
            }
            else{
                if(field.GetIsTeam()){
                    WeAreTeamMethod();
                }else{
                    if(isOnBorder&&field.GetIsMagneto()){
                        //nothing
                    }else if(field.GetIsSandPaper()&&isOnBorder){
                        radius-=field.GetSandPaper();
                        if(radius<=0){
                            thisThread.interrupt();
                        }
                        if(field.GetIsSnowBall()){
                            SnowBallMethod();}
                        x += speedX; y += speedY;
                        isOnBorder=false;
                    }
                    else{
                        if(field.GetIsSnowBall()){
                            SnowBallMethod();
                        }
                        x += speedX; y += speedY;
                        isOnBorder=false;
                    }
                }
            }
        }
        }
    }

    private void WeAreTeamMethod(){
        if(this.name.equals(field.GetName())){
            if(isOnBorder&&field.GetIsMagneto()){
            }else if(field.GetIsSandPaper()&&isOnBorder){
                radius-=field.GetSandPaper();
                if(radius<=0){
                    thisThread.interrupt();
                }
                if(field.GetIsSnowBall()){
                    SnowBallMethod();}
                x += speedX; y += speedY;
                isOnBorder=false;
            }
            else{
                if(field.GetIsSnowBall()){
                    SnowBallMethod();
                }
                x += speedX; y += speedY;
                isOnBorder=false;
            }
        }else{

        }
    }

    private boolean StopChecker(){
        if(field.GetIsStopSmallBalls()&&radius<10){
           if(!isStopped){
               field.AddCountStoppedBall();
               isStopped=true;
           }
            return true;
        }else if(16-speed+field.GetTimeMachine()>8&&field.GetIsStopSlowBalls()){
            if(!isStopped){
                field.AddCountStoppedBall();
                isStopped=true;
            }
            return true;}
        else if(isStopped){
        isStopped=false;
        field.MinusCountStoppedBall();
        }

        return false;
    }



}