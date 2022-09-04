/*****************
 textfieldの設定
******************/

package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

class PreFrame extends JFrame implements ActionListener,MouseListener,MouseMotionListener,WindowListener{
	private static final long serialVersionUID = 1L;
	Container cp;
	int debug = 0;
	boolean hoge = true;
	int[][] mapData = new int[23][17];
	Component comp = null;
	BufferedImage imag = null;
	Graphics2D g2 = null;
	JPanel pn = new JPanel();
	public static JPanel mainpanel = new JPanel();
	JButton nmap = new JButton("new map");
	JButton imap = new JButton("import map");
	JButton save = new JButton("save");
	JButton repaint = new JButton("repaint");
	JButton set = new JButton("Set");
	JLabel mclabel = new JLabel("MapChip",SwingConstants.CENTER);
	JLabel mousechip = new JLabel("Select Chip",SwingConstants.CENTER);
	JLabel coordinate = new JLabel("Coordinate",SwingConstants.CENTER);
	JLabel H = new JLabel("H",SwingConstants.CENTER);
	JLabel C = new JLabel("C",SwingConstants.CENTER);
	JLabel Turn = new JLabel("Turn",SwingConstants.CENTER);
	JTextField C_t_x = new JTextField("X");
	JTextField C_t_y = new JTextField("Y");
	JTextField H_t_x = new JTextField("X");
	JTextField H_t_y = new JTextField("Y");
	JTextField Turn_T = new JTextField("100");
	Point point = new Point();
	Point clientpoint = new Point();
	Point coor_c = new Point(-1,-1);
	Point coor_h = new Point(-1,-1);
	ChipData floor = new ChipData("floor");
	ChipData block = new ChipData("block");
	ChipData item = new ChipData("item");
	ChipData hot = new ChipData("hot");
	ChipData cool = new ChipData("cool");
	ChipData Mouse = new ChipData("floor");

	public PreFrame(String title) {	//動作
		for(int i=0;i<23;++i){
			for(int j=0;j<17;++j){
				mapData[i][j] = 0;
			}
		}
		cp = getContentPane();
		//System.out.println(getImage("hot.jpg"));

		//フレームのタイトル
		setTitle(title);

		init();

		//ウィンドウを閉じる時
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

	}

	public void init(){	//各種設定
		this.addWindowListener(this);
		pn.setLayout(new GridLayout(1,4));
		mainpanel.setLayout(null);
		setLocation(150,10);
		getLocation();
		setSize(1000,700);
		setResizable(false);

		mainpanel.addMouseListener(this);
		mainpanel.addMouseMotionListener(this);
		nmap.addActionListener(this);
		imap.addActionListener(this);
		save.addActionListener(this);
		repaint.addActionListener(this);
		set.addActionListener(this);

		mclabel.setBounds(800,50,50,20);
		mousechip.setBounds(800,314,67,20);
		coordinate.setBounds(800,400,70,20);
		C.setBounds(800,425,10,20);
		H.setBounds(800,455,10,20);
		C_t_x.setBounds(820,425,50,20);
		C_t_y.setBounds(880,425,50,20);
		H_t_x.setBounds(820,455,50,20);
		H_t_y.setBounds(880,455,50,20);
		set.setBounds(900,490,60,20);
		Turn.setBounds(800,520,30,20);
		Turn_T.setBounds(820,550,50,20);

		pn.add(nmap);
		pn.add(imap);
		pn.add(save);
		pn.add(repaint);
		mainpanel.add(mclabel);
		mainpanel.add(mousechip);
		mainpanel.add(coordinate);
		mainpanel.add(C);
		mainpanel.add(H);
		mainpanel.add(C_t_x);
		mainpanel.add(C_t_y);
		mainpanel.add(H_t_x);
		mainpanel.add(H_t_y);
		mainpanel.add(set);
		mainpanel.add(Turn);
		mainpanel.add(Turn_T);

		cp.add(pn,BorderLayout.SOUTH);
		cp.add(mainpanel,BorderLayout.CENTER);
		comp = cp;
		int w = 1000;
		int h = 700;
		imag = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		g2 = imag.createGraphics();
	}

	public void paint(Graphics g){
		make();
		g.drawImage(imag,0,0,this);
	}
	public void make(){
		int y = 50;
		int x = 20;
		int chipx = 21;
		int chipy = 51;
		Color color = new Color(0x00,0x00,0x00);

		super.paint(g2);
		g2.drawImage(floor.img,820,100,this);//112
		g2.drawImage(block.img,820,142, this);//154
		g2.drawImage(item.img,820,184,this);//196
		g2.drawImage(cool.img,820,226,this);
		g2.drawImage(hot.img,820,268,this);
		g2.drawImage(Mouse.img,820,364,this);
		g2.setColor(color);

		for(int i=0;i<=17;++i){
			g2.drawLine(20,y,756,y);
			y += 32;
		}
		for(int i=0;i<=23;++i){
			g2.drawLine(x,50,x,594);
			x += 32;
		}

		for(int i=0;i<17;++i){
			chipx = 21;
			for(int j=0;j<23;++j){
				switch(mapData[j][i]){
					case 0:
						g2.drawImage(floor.img,chipx,chipy,this);
						break;
					case 2:
						g2.drawImage(block.img,chipx,chipy,this);
						break;
					case 3:
						g2.drawImage(item.img,chipx,chipy,this);
						break;
					case 4:
						g2.drawImage(hot.img,chipx,chipy,this);
						break;
					case 5:
						g2.drawImage(cool.img,chipx,chipy,this);
						break;
				}
				chipx += 32;
			}
			chipy += 32;
		}
		//g2.dispose();
	}

	public void actionPerformed(ActionEvent e) { //ボタンが押された時の処理
		JFileChooser filechooser = new JFileChooser();
		Object obj = e.getSource();
		if(obj == nmap){
			for(int i=0;i<17;++i){
				for(int j=0;j<23;++j){
					mapData[j][i] = 0;
				}
			}
			C_t_x.setText("X");
			C_t_y.setText("Y");
			H_t_x.setText("X");
			H_t_y.setText("Y");
			coor_c.x = -1;
			coor_c.y = -1;
			coor_h.x = -1;
			coor_h.y = -1;
			repaint();
		}
		else if(obj == imap){
			File indir = new File("");
			int selected = filechooser.showOpenDialog(this);

			if (selected == JFileChooser.APPROVE_OPTION){
				//System.out.println("Load");
				indir = filechooser.getSelectedFile();
				Load(indir);
			}
			else if (selected == JFileChooser.CANCEL_OPTION){
				//System.out.println("Cancel");
			}
			else if (selected == JFileChooser.ERROR_OPTION){
				//System.out.println("Error");
			}
		}
		else if(obj == save){
			File dir = new File("");
			int selected = filechooser.showSaveDialog(this);
			if(selected == JFileChooser.APPROVE_OPTION){
				//System.out.println("Save");
				dir = filechooser.getSelectedFile();
				String filename = filechooser.getName(dir);
				save(dir,filename);
			}
			else if(selected == JFileChooser.CANCEL_OPTION){
				//System.out.println("Cancel");
			}
			else if(selected == JFileChooser.ERROR_OPTION){
				//System.out.println("Error");
			}
		}
		else if(obj == repaint){
			repaint();
		}
		else if(obj == set){
			int jaj = 0;
			boolean flag = true;
			boolean coolflag = true;
			boolean hotflag = true;
			String C_px = C_t_x.getText();
			String C_py = C_t_y.getText();
			String H_px = H_t_x.getText();
			String H_py = H_t_y.getText();

			if(coor_h.x != -1 && coor_h.y != -1){
				mapData[coor_h.x][coor_h.y] = 0;
				repaint(21+32*coor_h.x,51+32*coor_h.y,32,32);
			}

			if(coor_c.x != -1 && coor_c.y != -1){
				mapData[coor_c.x][coor_c.y] = 0;
				repaint(21+32*coor_c.x,51+32*coor_c.y,32,32);
			}

			//run;
			flag = NumberWord(C_px,C_py,H_px,H_py);
			if(flag){
				if(Integer.parseInt(C_px) != 1 && Integer.parseInt(C_py) != -1){
					coor_c.x = Integer.parseInt(C_px);
					coor_c.y = Integer.parseInt(C_py);
				}
				else{
					coolflag = false;
				}
				if(Integer.parseInt(H_px) != -1 && Integer.parseInt(H_py) != -1){
					coor_h.x = Integer.parseInt(H_px);
					coor_h.y = Integer.parseInt(H_py);
				}
				else{
					hotflag = false;
				}
			}

			mapData[coor_h.x][coor_h.y] = 4;
			mapData[coor_c.x][coor_c.y] = 5;

			if(coolflag){
				repaint(21+32*coor_c.x,51+32*coor_c.y,32,32);
			}
			if(hotflag){
				repaint(21+32*coor_h.x,51+32*coor_h.y,32,32);
			}
		}
	}

	public void save(File file,String name){
		if(file == null){
			System.out.println("FileNotFound");
		}
		else{
			try{
				int len = name.length();
				int[][] saveMap = new int[23][17];
				String turn_set = Turn_T.getText();
				String C_px = C_t_x.getText();
				String C_py = C_t_y.getText();
				String H_px = H_t_x.getText();
				String H_py = H_t_y.getText();
				String path = file.getPath();
				StringBuilder temp = new StringBuilder(name);
				FileWriter out = new FileWriter(file);

				path = path.replaceAll(".map",".jpeg");
				//comp.paint(g2);
				//g2.dispose();
				temp.delete(len-4,len);

				OutputStream outfile = new FileOutputStream(path);
				ImageIO.write(imag,"jpeg",outfile);
				//System.out.println(file.getPath());
				//System.out.println(out+"\n"+outfile);


				for(int i=0;i<17;++i){
					for(int j=0;j<23;++j){
						saveMap[j][i] = mapData[j][i];
					}
				}

				out.write("N:"+temp+"\r\n");
				out.write("S:23,17\r\n");
				out.write("T:"+turn_set+"\r\n");

				if(coor_c.x != -1 && coor_c.y != -1 && coor_h.x != -1 && coor_h.y != -1){
					boolean flag = true;
					flag = NumberWord(C_px,C_py,H_px,H_py);
					if(flag){
						if(coor_c.x != (Integer.parseInt(C_px)) && coor_c.y != (Integer.parseInt(C_py)) && coor_h.x != (Integer.parseInt(H_px)) && coor_h.y != (Integer.parseInt(H_py))){
							H_px = Integer.toString(coor_h.x);
							H_py = Integer.toString(coor_h.y);
							C_px = Integer.toString(coor_c.x);
							C_py = Integer.toString(coor_c.y);
						}
					}
					else{
						H_px = Integer.toString(coor_h.x);
						H_py = Integer.toString(coor_h.y);
						C_px = Integer.toString(coor_c.x);
						C_py = Integer.toString(coor_c.y);
					}
					saveMap[coor_c.x][coor_c.y] = 0;
					saveMap[coor_h.x][coor_h.y] = 0;
				}
				else{
					H_px = Integer.toString(0);
					H_py = Integer.toString(0);
					C_px = Integer.toString(0);
					C_py = Integer.toString(0);
				}

				for(int i=0;i<17;++i){
					out.write("D:");
					for(int j=0;j<23;++j){
						out.write('0'+saveMap[j][i]);
						if(j<22){
							out.write(",");
						}
					}
					out.write("\r\n");
				}
				out.write("H:"+H_px+","+H_py);
				out.write("\r\n");
				out.write("C:"+C_px+","+C_py);
				out.close();
			}
			catch(IOException e){
				System.out.println(e);
			}
		}
	}

	public void Load(File file){
		if(file == null){
			System.out.println("Error");
		}
		else {
			try{
				String turn;
				String line;
				int hoge;
				FileReader filereder = new FileReader(file);
				BufferedReader br = new BufferedReader(filereder);
				if(null == br.readLine()){
					System.out.println("読み込みファイルが不正");
					System.exit(0);
				}
				br.readLine();
				turn = br.readLine();
				turn = turn.replace("T", "");
				turn = turn.replace(":", "");
				Turn_T.setText(turn);
				/*System.out.println(turn);
				hoge = filereder.read();
				System.out.println(hoge);
				hoge = filereder.read();
				System.out.println(hoge);*/

				for(int i=0;i<17;++i){
					line = br.readLine();
					ChangeMapData(line,i);
				}

				/*for(int i=0;i<17;++i){
					for(int j=0;j<23;++j){
						System.out.print(mapData[j][i] + " ");
					}
					System.out.println();
				}*/

				line = br.readLine();
				H_t_x.setText(NumberString(line,0));
				H_t_y.setText(NumberString(line,1));
				coor_h.x = Integer.parseInt(NumberString(line,0));
				coor_h.y = Integer.parseInt(NumberString(line,1));
				mapData[coor_h.x][coor_h.y] = 4;

				line = br.readLine();
				C_t_x.setText(NumberString(line,0));
				C_t_y.setText(NumberString(line,1));
				coor_c.x = Integer.parseInt(NumberString(line,0));
				coor_c.y = Integer.parseInt(NumberString(line,1));
				mapData[coor_c.x][coor_c.y] = 5;
				repaint();
			}
			catch(IOException e){
			}
		}
	}

	public String NumberString(String str,int mode){
		int len = str.length();
		String hoge = "";

		if(mode == 0){
			hoge += str.charAt(2);
			if(str.charAt(3) != ','){
				hoge += str.charAt(3);
			}
		}
		else{
			if(str.charAt(4) == ','){
				hoge += str.charAt(5);
				if(len == 7){
					hoge += str.charAt(6);
				}
			}
			else{
				hoge += str.charAt(4);
				if(len == 6){
					hoge += str.charAt(5);
				}
			}
		}
		return hoge;
	}

	@SuppressWarnings("finally")
	public boolean NumberWord(String str_1,String str_2,String str_3,String str_4){
		boolean flag = true;
		try{
			Integer.parseInt(str_1);
			Integer.parseInt(str_2);
			Integer.parseInt(str_3);
			Integer.parseInt(str_4);
		}
		catch(NumberFormatException e){
			flag = false;
		}
		finally{
			return flag;
		}
	}

	public void ChangeMapData(String data,int row){
		for(int i=2,cnt=0;i<47;i+=2,cnt++){
			mapData[cnt][row] = data.charAt(i)-'0';
		}
	}

	public void mouseClicked(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e){
		point = e.getPoint();
		//System.out.println(point.x);
		clickedEvent(point);
	}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}

	public void mouseDragged(MouseEvent e){
		point = e.getPoint();
		clickedEvent(point);
	}
	public void mouseMoved(MouseEvent e){}

	public Point coorChange(Point coor){
		Point temp = new Point(coor);

		temp.x -= 15;
		temp.y -= 20;

		temp.x /= 32;
		temp.y /= 32;

		return temp;
	}

	public void clickedEvent(Point p){
		Point temp = new Point(p);

		//System.out.println("clicked Event call");
		if((temp.x >= 15 && temp.x <= 750) && (temp.y >= 25 && temp.y <= 562)){
			clientpoint = coorChange(temp);
			//System.out.println("map Clicked!");
			if(Mouse.chipdata != 4 && Mouse.chipdata != 5){
				if(mapData[clientpoint.x][clientpoint.y] != Mouse.chipdata){
					mapData[clientpoint.x][clientpoint.y] = Mouse.chipdata;
					repaint(21+32*clientpoint.x,51+32*clientpoint.y,32,32);
				}
			}
			else{
				if(Mouse.chipdata == 4){
					if(coor_h.x != -1 && coor_h.y != -1){
						mapData[coor_h.x][coor_h.y] = 0;
					}
					repaint(21+32*coor_h.x,51+32*coor_h.y,32,32);
					coor_h.x = clientpoint.x;
					coor_h.y = clientpoint.y;
				}
				else if(Mouse.chipdata == 5){
					if(coor_c.x != -1 && coor_c.y != -1){
						mapData[coor_c.x][coor_c.y] = 0;
					}
					repaint(21+32*coor_c.x,51+32*coor_c.y,32,32);
					coor_c.x = clientpoint.x;
					coor_c.y = clientpoint.y;
				}
				//run;
				mapData[clientpoint.x][clientpoint.y] = Mouse.chipdata;
				repaint(21+32*clientpoint.x,51+32*clientpoint.y,32,32);
			}
			//System.out.println(clientpoint.x);
			//System.out.println(clientpoint.y);
		}
		else if((temp.x >= 800 && temp.x <= 860) && (temp.y >= 55 && temp.y <= 320)){
			//System.out.println("chip Select call!");
			Mouse.chipdata = chipSelect(p,Mouse.chipdata);
			Mouse = new ChipData(Mouse.chipdata);
			repaint(820,364,32,32);
		}
	}

	public void run(){
		try{
			Thread.sleep(20);
		}
		catch (InterruptedException e){
			System.out.println(e);
		}
	}

	public int chipSelect(Point select,int old_mode){
		Point temp = new Point(select);

		if(temp.x >= 817 && temp.x <= 848){
			if(temp.y >= 70 && temp.y <= 101){
				return 0;
			}
			else if(temp.y >= 112 && temp.y <= 143){
				return 2;
			}
			else if(temp.y >= 155 && temp.y <= 186){
				return 3;
			}
			else if(temp.y >= 196 && temp.y <= 228){
				return 5;
			}
			else if(temp.y >= 238 && temp.y <= 270){
				return 4;
			}
			else return old_mode;
		}
		else return old_mode;
	}

	@Override
	public void windowActivated(WindowEvent e){
		repaint();
	}

	@Override
	public void windowClosed(WindowEvent e){}

	@Override
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e){}

	@Override
	public void windowDeiconified(WindowEvent e){}

	@Override
	public void windowIconified(WindowEvent e){}

	@Override
	public void windowOpened(WindowEvent e) {
		repaint();
	}
}

class ChipData{
	String str = "res/floor.jpg";
	ClassLoader cl = this.getClass().getClassLoader();
	Image img;
	int chipdata;

	ChipData(String str){
		//System.out.println(u);
		int mode = 0;

		if(str == "floor")mode = 0;
		else if(str == "block")mode = 2;
		else if(str == "item")mode = 3;
		else if(str == "hot")mode = 4;
		else if(str == "cool")mode = 5;
		try{
			switch(mode){
				case 0:
					URL u_floor = this.getClass().getClassLoader().getResource("res/floor.jpg");
					this.chipdata = 0;
					img = new ImageIcon(u_floor).getImage();
					break;
				case 2:
					URL u_block = this.getClass().getClassLoader().getResource("res/block.jpg");
					this.chipdata = 2;
					img = new ImageIcon(u_block).getImage();
					break;
				case 3:
					URL u_item = this.getClass().getClassLoader().getResource("res/item.jpg");
					this.chipdata = 3;
					img = new ImageIcon(u_item).getImage();
					break;
				case 4:
					URL u_hot = this.getClass().getClassLoader().getResource("res/hot.jpg");
					this.chipdata = 4;
					img = new ImageIcon(u_hot).getImage();
					break;
				case 5:
					URL u_cool = this.getClass().getClassLoader().getResource("res/cool.jpg");
					this.chipdata = 5;
					img = new ImageIcon(u_cool).getImage();
					break;
				default:
					//System.out.println("Error:"+str);
			}
		}
		catch(NullPointerException e){
			try{
				switch(mode){
					case 0:
						this.chipdata = 0;
						ImageIcon icon_f = new ImageIcon("floor.jpg");
						this.img = icon_f.getImage();
						break;
					case 2:
						this.chipdata = 2;
						ImageIcon icon_b = new ImageIcon("block.jpg");
						this.img = icon_b.getImage();
						break;
					case 3:
						this.chipdata = 3;
						ImageIcon icon_i = new ImageIcon("item.jpg");
						this.img = icon_i.getImage();
						break;
					case 4:
						this.chipdata = 4;
						ImageIcon icon_h = new ImageIcon("hot.jpg");
						this.img = icon_h.getImage();
						break;
					case 5:
						this.chipdata = 5;
						ImageIcon icon_c = new ImageIcon("cool.jpg");
						this.img = icon_c.getImage();
						break;
					default:
						//System.out.println("Error:"+str);
				}
			}
			catch(NullPointerException e2){
				System.out.println(e2);
			}
		}
	}
	ChipData(int data){
		try{
			switch(data){
				case 0:
					URL u_floor = this.getClass().getClassLoader().getResource("res/floor.jpg");
					this.chipdata = 0;
					img = new ImageIcon(u_floor).getImage();
					break;
				case 2:
					URL u_block = this.getClass().getClassLoader().getResource("res/block.jpg");
					this.chipdata = 2;
					img = new ImageIcon(u_block).getImage();
					break;
				case 3:
					URL u_item = this.getClass().getClassLoader().getResource("res/item.jpg");
					this.chipdata = 3;
					img = new ImageIcon(u_item).getImage();
					break;
				case 4:
					URL u_hot = this.getClass().getClassLoader().getResource("res/hot.jpg");
					this.chipdata = 4;
					img = new ImageIcon(u_hot).getImage();
					break;
				case 5:
					URL u_cool = this.getClass().getClassLoader().getResource("res/cool.jpg");
					this.chipdata = 5;
					img = new ImageIcon(u_cool).getImage();
					break;
			}
		}
		catch(NullPointerException e){
			switch(data){
				case 0:
					this.chipdata = 0;
					ImageIcon icon_f = new ImageIcon("floor.jpg");
					this.img = icon_f.getImage();
					break;
				case 2:
					this.chipdata = 2;
					ImageIcon icon_b = new ImageIcon("block.jpg");
					this.img = icon_b.getImage();
					break;
				case 3:
					this.chipdata = 3;
					ImageIcon icon_i = new ImageIcon("item.jpg");
					this.img = icon_i.getImage();
					break;
				case 4:
					this.chipdata = 4;
					ImageIcon icon_h = new ImageIcon("hot.jpg");
					this.img = icon_h.getImage();
					break;
				case 5:
					this.chipdata = 5;
					ImageIcon icon_c = new ImageIcon("cool.jpg");
					this.img = icon_c.getImage();
					break;
				default:
			}
		}
	}
}

public class Main {
	public static void main(String[] args)throws InterruptedException{
		PreFrame frm = new PreFrame("MapEditer");
		frm.setBackground(Color.WHITE);
		frm.setVisible(true);
	}
}
