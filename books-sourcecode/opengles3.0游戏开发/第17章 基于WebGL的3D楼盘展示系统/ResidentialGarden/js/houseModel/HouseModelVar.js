//==================���ƶ���start================//
var building;//¥��ģ��
var rectangle;//����2D����Ļ��ƶ���
var button;//���ư�ť����
var grand;//��ƺ���ƶ���
var trees=new Array();//���Ķ���
var imageName;//ģ��ͼƬ��
var objName;//ģ����
//==================���ƶ���end================//

//==================���Ļ���λ������ start==========
var point0=[-16,14,15,14,-12,-12,16,-14];//������λ�õ�����--��8
var point1=[-12,12,12,12,-13,-15,12,-16];//������λ�õ�����---��10
var point2=[-16,9,16,15,-16,-10,10,-12];//������λ�õ�����---��6
var bs=1;//���Ĵ�С����
//==================���Ļ���λ������ end==========

var ms2D=new MatrixState();//2D����ı仯����
var uriIndex="1";//Ҫ���Ʒ��ӵ�����ֵ

//============��Ļ����=======start==============//
var SCREEN_WIDTH_STANDARD=1200;//��Ļ��׼���
var SCREEN_HEIGHT_STANDARD=600;//��Ļ��׼�߶�
var RATIO=SCREEN_WIDTH_STANDARD/SCREEN_HEIGHT_STANDARD;//��Ļ��׼����--��͸��ͶӰ�ı���
//============��Ļ����=======end==============//

//==================��ť����λ�õ�����start================//
var HUXING_X=-1.5;
var HUXING_Y=0.014;
//һ��һ�����������
var ONEROOM_X=-1.56;
var ONEROOM_Y=0.24;
var ONEROOM_MINX=-1.82;
var ONEROOM_MAXX=-1.25;
var ONEROOM_MINY=0.14;
var ONEROOM_MAXY=0.32;
//����һ�����������
var TOWROOM_X=-1.56;
var TOWROOM_Y=-0.08;
var TOWROOM_MINX=-1.82;
var TOWROOM_MAXX=-1.25;
var TOWROOM_MINY=-0.18;
var TOWROOM_MAXY=0.007;
//����һ�����������
var THREEROOM_X=-1.56;
var THREEROOM_Y=-0.4;
var THREEROOM_MINX=-1.82;
var THREEROOM_MAXX=-1.25;
var THREEROOM_MINY=-0.51;
var THREEROOM_MAXY=-0.32;
//���ذ�ť
var returnX=1.7;//����ͼ����2D�е�x����
var returnY=-0.84;//����ͼ����2D�е�y����
var return_minX=returnX-0.25/2;//ͼ��x�������ֵ
var return_maxX=returnX+0.25/2;//ͼ��x�����ұ�ֵ
var return_minY=returnY-0.25/2;//ͼ��y�����±�ֵ
var return_maxY=returnY+0.25/2;//ͼ��y�����ϱ�ֵ

var ONE=0;//ONEͼƬ����ֵ
var TWO=2;//TWOͼƬ����ֵ
var THREE=4;//THREEͼƬ����ֵ
var PIC_NAME=["label9_1","label9_2","label8_1","label8_2","label7_1","label7_2",];
//==================��ť����λ�õ�����end================//

//==================������������start================//
var cameraX=0;//�����xλ��
var cameraY=120;//�����yλ��
var cameraZ=1050;//�����zλ��	
	
var targetX=0;//Ŀ���xλ��
var targetY=26;//Ŀ���yλ��
var targetZ=0;//Ŀ���zλ��

var upX=0;
var upY=1;
var upZ=0;

var tempx=upX+cameraX;//�м�ֵx
var tempz=upZ+cameraZ;//�м�ֵz
var tempLimit=tempz;

var currSightDis=1000;//�������Ŀ��ľ���
var angdegElevation=5;//����
var angdegAzimuth=0;//��λ��
var TOUCH_SCALE_FACTOR = 180.0/320;//�Ƕ����ű���

var Vtop=1;
var Vnear=30;
var V2Dnear=1;//2D��
var Vfar=50000;

//==================������������end================//	

var isReturn=false;//�Ƿ񷵻�������
var isRoomType=false;//ת��������͵�ҳ��ı�־λ
var roomTypeIndex=0;//����id
var count=0;//ͣ��ʱ��
var displaySize=16;//ÿһ��ĳߴ�