//=============���ƶ���start====================//
var obj;//���ζ���
var trees=new Array();//���Ķ���
var mainView_2D;//�����������2D����
var mainView_rectangle;//�����������2D����
var grand;//��ƺ���ƶ���
var building;//¥��ģ��	
var lable=new Array(8);//������ť����
var lable_down=new Array(6);//������ť����
//=============���ƶ���end====================//

//=============��Ļ�������start====================//
var SCREEN_WIDTH_STANDARD=1200;//��Ļ��׼���
var SCREEN_HEIGHT_STANDARD=600;//��Ļ��׼�߶�
var RATIO=SCREEN_WIDTH_STANDARD/SCREEN_HEIGHT_STANDARD;//��Ļ��׼����--��͸��ͶӰ�ı���
//=============��Ļ�������end====================//
//=============��Ļ�������end====================//

//=============2D����start====================//
var ms2D=new MatrixState();//2D����ľ���
//=============2D����end====================//

//=================��ťλ������ start==============
//�����������
var CRUISEGO_LEFT=975;
var CRUISEGO_RIGHT=1140;
var CRUISEGo_TOP=251;
var CRUISEGo_BUTTOM=328;
//���������������
var FREETOWALK_LEFT=975;
var FREETOWALK_RIGHT=1140;
var FREETOWALK_TOP=401;
var FREETOWALK_BUTTOM=479;
//���ģ��1
var MODEL1_LEFT=93;
var MODEL1_RIGHT=240;
var MODEL1_TOP=212;
var MODEL1_BUTTOM=267;
//���ģ��2
var MODEL2_LEFT=93;
var MODEL2_RIGHT=240;
var MODEL2_TOP=294;
var MODEL2_BUTTOM=347;
//���ģ��3
var MODEL3_LEFT=93;
var MODEL3_RIGHT=240;
var MODEL3_TOP=372;
var MODEL3_BUTTOM=449;
//���ģ��4
var MODEL4_LEFT=93;
var MODEL4_RIGHT=240;
var MODEL4_TOP=453;
var MODEL4_BUTTOM=507;
//=================��ťλ������ end==============

//==================������������� start==========
var Vtop=1;
var Vnear=30;//3D��--��ƽ��
var Vfar=30000;//Զƽ��
var V2Dnear=1;//2D��--��ƽ��

var cameraX=0;//�����λ��xֵ
var cameraY=120;//�����yλ��
var cameraZ=1050;//�����zλ��
var cameraLimit=cameraZ;
var targetX=0;//Ŀ���xλ��
var targetY=26;//Ŀ���yλ��
var targetZ=0;//�����Ŀ���zλ��
var upX=0;//�����up����xֵ
var upY=1;//�����up����yֵ
var upZ=0;//�����up����zֵ
var tempx=upX+cameraX;//�м�ֵx
var tempz=upZ+cameraZ;//�м�ֵz
var tempLimit=tempz;
var degree=0;//��ת�Ƕ�
//==================������������� end==========

var isDown=new Array(0,0,0,0,0,0);//�ж��Ƿ񱻰���
var displaySize=16;//ÿһ��ĳߴ�
var openIndex=-1;//ģ��id

//==================���Ļ���λ������ start==========
var point0=[-16,14,15,14,-12,-12,16,-14];//������λ�õ�����--��8
var point1=[-12,12,12,12,-13,-15,12,-16];//������λ�õ�����---��10
var point2=[-16,9,16,15,-16,-10,10,-12];//������λ�õ�����---��6
//==================���Ļ���λ������ end==========

//===================ҳ����ת�ı�־λstart================
var isHouseModel=false;//�л������¥��ģ�ͽ���
var isRoomType=false;//�л����鿴���ͽ���
var isCruiseGo=false;//�л�������ģʽ����
var isOpen=false;//�л������С��ȫ����ҳ��
//===================ҳ����ת�ı�־λend================