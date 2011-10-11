

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.util.HashMap;


public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    private GameController controller;
    private boolean mouseLeftIsDown;
    private boolean mouseRightIsDown;
    public HashMap<Integer, Boolean> keysDown;
    
    public InputHandler() {
        super();
        keysDown = new HashMap<Integer, Boolean>();
        mouseLeftIsDown = false;
        mouseRightIsDown = false;
    }

    public void doActions(){
        // Do actions based on currently pressed keys. Such as walking and stuff.
        if(controller.settings.keyBoardSettings.equals("WASD")){
            doWASD();
        }else{
            doWESD();
        }
        if(mouseLeftIsDown) {
        	controller.characterFire();
        }
    }


    public void setGameController(GameController c){
        controller = c;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {  // add a pressed key to the list
        keysDown.put(e.getKeyCode(),true); // Do I need to copy this? Or is it generated "anew" every time?
    }

    @Override
    public void keyReleased(KeyEvent e) {  // remove pressed key from the list
        keysDown.put(e.getKeyCode(),false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()== MouseEvent.BUTTON1){
            mouseLeftIsDown = true;
        }
        if(e.getButton()== MouseEvent.BUTTON2){
            mouseRightIsDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()== MouseEvent.BUTTON1){
            mouseLeftIsDown = false;
        }
        if(e.getButton()== MouseEvent.BUTTON2){
            mouseRightIsDown = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //controller.moveViewRelative(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	rotateCharacter(e);
    }
    
    private void doWASD(){
        if(keysDown.get(KeyEvent.VK_W)){
            controller.rotateCurrentChar(-0.5f);
        }
        if(keysDown.get(KeyEvent.VK_A)){
            controller.moveCurrentChar(new Vec3(1,0,0));
        }
        if(keysDown.get(KeyEvent.VK_S)){
            controller.moveCurrentChar(new Vec3(-1,0,0));
        }
        if(keysDown.get(KeyEvent.VK_D)){
            controller.rotateCurrentChar(0.5f);
        }
    }
    private void doWESD(){
        if(keysDown.get(KeyEvent.VK_W)!=null && keysDown.get(KeyEvent.VK_W)){
            controller.moveCurrentCharAbs(new Vec3(-1,0,0));
        }
        if(keysDown.get(KeyEvent.VK_E)!=null && keysDown.get(KeyEvent.VK_E)){
            controller.moveCurrentCharAbs(new Vec3(0,-1,0));
        }
        if(keysDown.get(KeyEvent.VK_D)!=null && keysDown.get(KeyEvent.VK_D)){
            controller.moveCurrentCharAbs(new Vec3(1,0,0));
        }
        if(keysDown.get(KeyEvent.VK_S)!=null && keysDown.get(KeyEvent.VK_S)){
            controller.moveCurrentCharAbs(new Vec3(0,1,0));
        }
    }
    private void rotateCharacter(MouseEvent e){
    	Vec3 dv = controller.getCurrentCharacterScreenLoc();
    	int dx = e.getX()-(int)dv.x;
    	int dy = e.getY()-(int)dv.y;
    	
    	controller.rotateCurrentChar((float)Math.atan(dy/dx));
    	
    }
}
