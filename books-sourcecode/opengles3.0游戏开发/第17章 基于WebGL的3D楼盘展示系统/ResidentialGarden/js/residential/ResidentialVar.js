//==========����start=====//
var ms2D=new MatrixState();//2D����ľ���
ms2D.setInitStack();//��ʼ���任����
ms.setInitStack();//��ʼ��3D�任����
//==========����end=====//

//==========Ҫ���Ƶ�3D����ģ��=====start=====//
var map;//��ͼģ�Ͷ���
var buildings=new Array();//¥��ģ�Ͷ���
var trees=new Array();//���Ķ���
var flower;//���Ķ���
var drawHouse;//����¥���Ķ���
var loadland;//���Ʋ�ƺ
var skybox;//��պ�
var rectangle;//������ζ�������
var fence;//դ��ģ�Ͷ���
var pool;//��Ȫģ�Ͷ���
var grand;//��ƺģ�Ͷ���
var button;//��ť���ƶ���
//==========Ҫ���Ƶ�3D����ģ��=====end=======//

//============��־λ=======start==============//
var isGo=false;//�Ƿ񲥷����ζ���
var isChange=false;//�ı䷽��
var isReturn=false;//�Ƿ񷵻�
//============��־λ=======end==============//

//============Button=======start==============//
var goOrStopX=-1.85;//��ͣ������ͼ����2D�е�x����
var goOrStopY=-0.8;//��ͣ������ͼ����2D�е�y����
var goOrStop_HalfSize=0.25/2.0;//��ͣ������ͼ��İ����߰볤
var goOrStop_minX=goOrStopX-goOrStop_HalfSize;//ͼ��x�������ֵ
var goOrStop_maxX=goOrStopX+goOrStop_HalfSize;//ͼ��x�����ұ�ֵ
var goOrStop_minY=goOrStopY-goOrStop_HalfSize;//ͼ��y�����±�ֵ
var goOrStop_maxY=goOrStopY+goOrStop_HalfSize;//ͼ��y�����ϱ�ֵ

var directionX=-1.85;//ҡ��ͼ����2D�е�x����
var directionY=-0.8;//ҡ��ͼ����2D�е�y����
//ǰ����
var up_minX=-1.86;
var up_maxX=-1.78;
var up_minY=-0.75;
var up_maxY=-0.72;
//���˼�
var down_minX=-1.86;
var down_maxX=-1.78;
var down_minY=-0.93;
var down_maxY=-0.89;
//���Ƽ�
var left_minX=-1.93;
var left_maxX=-1.89;
var left_minY=-0.857;
var left_maxY=-0.79;
//���Ƽ�
var right_minX=-1.756;
var right_maxX=-1.73;
var right_minY=-0.857;
var right_maxY=0.79;

var direction=-1;//ҡ�˷���--0-up;1-down;2-left;3-right
var vt=0;//�ƶ��ٶ�
//���ذ�ť
var returnX=1.7;//����ͼ����2D�е�x����
var returnY=-0.84;//����ͼ����2D�е�y����
var return_minX=returnX-0.25/2;//ͼ��x�������ֵ
var return_maxX=returnX+0.25/2;//ͼ��x�����ұ�ֵ
var return_minY=returnY-0.25/2;//ͼ��y�����±�ֵ
var return_maxY=returnY+0.25/2;//ͼ��y�����ϱ�ֵ

//���ͼ���������
var HF_X=0;
var HF_Y=0.77;
//============Button=======end==============//

//============��Ļ����=======start==============//
var SCREEN_WIDTH_STANDARD=1200;//��Ļ��׼���
var SCREEN_HEIGHT_STANDARD=600;//��Ļ��׼�߶�
var RATIO=SCREEN_WIDTH_STANDARD/SCREEN_HEIGHT_STANDARD;//��Ļ��׼����--��͸��ͶӰ�ı���
//============��Ļ����=======end==============//
var Vtop=1;
var Vnear=1;//3D��
var Vfar=1000000;
var skyboxSize=500000;
var V2Dnear=1;//2D��

//===========�ƶ��������Ҫ������=========start======//
var v=0.5;//�ƶ��ٶ�
var bs=0.5;//���ű���
var points=[
	0,196*bs,0,-v,
	0,77*bs,-v,0,
	-78.4*bs,77*bs,0,-v,
	-78.4*bs,-77*bs,v,0,
	0,-77*bs,0,-v,
	0,-196*bs,0,v,
	0,-77*bs,v,0,
	78.4*bs,-77*bs,0,v,
	78.4*bs,77*bs,-v,0,
	0,77*bs,0,v,
	0,196*bs,0,-v];//·������
var vx=points[2];//x���ϵ��ٶ�
var vz=points[3];//z���ϵ��ٶ�
var currentAngle=0;//Ŀ����ת�Ƕ�

var DISTANCE_CAMERA_YACHT=2;//�����λ�þ���۲�Ŀ����ƽ�����
var CAMERA_INI_Y=1.67;//�����y����

var index=1;//·�����ݵ�����ֵ
var px=points[0];//Ŀ����x����
var pz=points[1];//Ŀ����z����

var mapminX=-196*bs;
var mapmaxX=196*bs;
var mapminZ=-196*bs;
var mapmaxZ=196*bs;
//===========�ƶ��������Ҫ������===========end=======//

var uriIndex="0";//����һҳ�洫�����Ĳ���ֵ
//=================¥������  start=================
var building2=[19.5,-30,52.5,-30,81.5,-30];//¥��2��λ������--w19,L20,h30(x,z)
var building8=[18.5,-30,45.5,-30,72.5,-30];//¥��8��λ������--w15,L20,h46(x,z)
var building4=[23.6,-21.6,36.4,-60.4];//¥��4��λ������--w31.2,L31.2,h92(x,z)
var building5=[30.2,-21,29.8,-59];//¥��5��λ������--w44.4,L30,h57.6(x,z)

var building01=[22,-30,50,-30,78,-30];//¥��01��λ������--w16,L27,h20(x,z)
var building02=[21,-30,51,-30,81,-30];//¥��02��λ������--w18,L23,h11(x,z)
//=================¥������  start=================
var land=[-95,42.3,95,42.3,-95,-36,95,-36,-95,-94.3,95,-94.3,-36,-36];//���Ʋ�ƺ���ƶ���һ��λ��
var landSize=4;//��ƺ�ߴ��С
var MapHalfSize=100;//��ƺһ��Ĵ�С
