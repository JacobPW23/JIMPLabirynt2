import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;


public class MazeField{

		private Color color;
        private Color highlightColor;
		private Rectangle2D.Float shape;
        public static int wallSize;
        private float xBegining;
        private float yBegining;
        private Graphics2D g2D;

		public MazeField(char x,int i,int j){
				if(x=='X'){
                    color=Color.BLACK;
                }
                else{
                    color=Color.WHITE;
                }
                wallSize=10;
                highlightColor=Color.red;
				this.shape=new Rectangle2D.Float(i*wallSize,j*wallSize,wallSize,wallSize);
                
		}
		
		public Color getColor(){
			return color;
		}
		public void setColor(Color color){
			this.color=color;
		}

		public Rectangle2D.Float getShape(){
			return shape;
		}

		public void setShape(Rectangle2D.Float shape){
			this.shape=shape;
		}
        public void draw(){

            g2D.setColor(color);
            g2D.fill(shape);
        }

        public void setBegining(float xBegining, float yBegining)
        {
            this.xBegining=xBegining;
            this.yBegining=yBegining;
            shape.x+=xBegining;
            shape.y+=yBegining;


        }


        public void setGraphics(Graphics2D g){
            g2D=g;
        
        }
        public void highlight(){
            
            g2D.setColor(highlightColor);
            g2D.fill(shape);
        }

        public void clearHighlight(){

            g2D.setColor(color);
            g2D.fill(shape);
        }
		

	}