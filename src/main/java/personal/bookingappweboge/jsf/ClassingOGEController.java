package personal.bookingappweboge.jsf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import personal.bookingappweboge.entities.ClassingOGE;
import personal.bookingappweboge.jsf.util.JsfUtil;
import personal.bookingappweboge.jsf.util.PaginationHelper;
import personal.bookingappweboge.session.ClassingOGEFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.bookingappweboge.entities.StudentOGE;
//new code
import personal.bookingappweboge.entities.ClassrOGE;


@Named("classingOGEController")
@SessionScoped
public class ClassingOGEController implements Serializable {

    private ClassingOGE current;
    private DataModel items = null;
    @EJB
    private personal.bookingappweboge.session.ClassingOGEFacade ejbFacade;
    
    //added later for creating a booking object
    @EJB
    private personal.bookingappweboge.session.ClassrOGEFacade classrFacade;
    @EJB
    private personal.bookingappweboge.session.TeacherOGEFacade teacherFacade;
    @EJB
    private personal.bookingappweboge.session.TeamOGEFacade teamFacade;
    @EJB
    private personal.bookingappweboge.session.OffenceOGEFacade offenceFacade;
    @EJB
    private personal.bookingappweboge.session.StudentOGEFacade studentFacade;
    @EJB
    private personal.bookingappweboge.entities.ClassrOGE classrOGE;
    @EJB
    private personal.bookingappweboge.entities.TeacherOGE teacherOGE;
    @EJB
    private personal.bookingappweboge.entities.TeamOGE teamOGE;
    @EJB
    private personal.bookingappweboge.entities.OffenceOGE offenceOGE;
    @EJB
    private personal.bookingappweboge.entities.StudentOGE studentOGE;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    
    //added logger
    final Logger logger = LoggerFactory.getLogger(ClassingOGEController.class);
    

    public ClassingOGEController() {
    }
    public List<StudentOGE> findStudentsInClasr(Integer classrId){
        System.out.println("fsic.findStudentsInClasr("+classrId+")");
        return ejbFacade.findStudentsInClasr(classrId);
    }
    public String updateStudentClassAssignments(FileUploadEvent event){
        
        String toreturn = "successful";
        try{
        UploadedFile updf = event.getFile();
        System.out.println("updf.getFileName()"+updf.getFileName());
        XSSFWorkbook excelQuestionBankWorkbook = new XSSFWorkbook(updf.getInputStream());
        System.out.println("excelQuestionBankWorkbook.getNumberOfSheets()"+excelQuestionBankWorkbook.getNumberOfSheets());
        XSSFSheet excelStudentClassroomAssignmentSheet = excelQuestionBankWorkbook.getSheetAt(0);
        System.out.println("excelStudentClassroomAssignmentSheet.getLastRowNum()"+excelStudentClassroomAssignmentSheet.getLastRowNum());
        /*
         * format of excelsheet expected
         * |s/n|surname|firstname|secondname|othernames|school|level|arm|dateofclassing|
         * *****header not included in upload****
         * -----------------------------------------------------------------
         * |100|Okolo  |Chidinma |Ifeanyi   |John       |JSS    |1    |A  |22-09-2022    |
         * .
         * .
         * .
         * |end|       |         |          |           |       |     |   |              |
         */
        boolean emptyAColumnCellMet = false;
        String[] mcq;
        Iterator<Row> iteratorRow = excelStudentClassroomAssignmentSheet.iterator();
        Row row=iteratorRow.next();
        System.out.println("row.getLastCellNum()="+row.getLastCellNum());
        Iterator<Cell> iteratorCell;
        Cell cell;
        iteratorCell = row.iterator();
        System.out.println("ok1");
        ArrayList<String[]> studentClassroomAssignmentArray = new ArrayList<>();
        int counter=0;
        /*
         * obtain each row from the spreadsheet and keep in an array
         */
        while(emptyAColumnCellMet==false){
            System.out.println("ok"+counter++);
            cell = iteratorCell.next();
            if(cell==null){
                System.out.println("ClassingOGEController:updateStudentClassAssignments: first cell content is null");
                emptyAColumnCellMet=true;
                break;
            }
            if(cell.getCellType().equals(CellType.BLANK)){
                System.out.println("ClassingOGEController:updateStudentClassAssignments: first cell content is blank");
                emptyAColumnCellMet=true;
                break;
            }
            if(cell.getCellType().equals(CellType.STRING)){
                if(cell.getStringCellValue().trim().equals("end")){
                    System.out.println("ClassingOGEController:updateStudentClassAssignments: end of excel file met");
                    emptyAColumnCellMet=true;
                    break;
                }
            }
            mcq = new String[9];
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[0]=cell.getStringCellValue();
            }else{
                mcq[0]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[0]+"/");
            cell=iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[1]=cell.getStringCellValue();
            }else{
                mcq[1]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[1]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[2]=cell.getStringCellValue();
            }else{
                mcq[2]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[2]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[3]=cell.getStringCellValue();
            }else{
                mcq[3]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[3]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[4]=cell.getStringCellValue();
            }else{
                mcq[4]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[4]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[5]=cell.getStringCellValue();
            }else{
                mcq[5]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[5]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[6]=cell.getStringCellValue();
            }else{
                mcq[6]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[6]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[7]=cell.getStringCellValue();
            }else{
                mcq[7]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[7]+"/");
            cell = iteratorCell.next();
            if(cell.getCellType().equals(CellType.STRING)){
                mcq[8]=cell.getStringCellValue();
            }else{
                mcq[8]= Integer.toString((int)cell.getNumericCellValue());
            }
            System.out.println(mcq[8]+"/");
            studentClassroomAssignmentArray.add(mcq);
            row=iteratorRow.next();
            iteratorCell = row.iterator();
        }
        System.out.println("ok2");
        System.out.println(studentClassroomAssignmentArray.size());
        /*
         * for each row of studentClassroomAssignmentArray check if student and
         * class exists already, if not create them in student and classr respectively
         * else associate them in classing
         * 
         */
        for(String[] temp: studentClassroomAssignmentArray){
            StudentOGE studentoge = studentFacade.findStudentWithNames(temp[1], temp[2], temp[3]);
            if(studentoge.getId()==-1){
                //does not exist, hence create it
                studentoge = studentFacade.createStudentOGE(temp[1], temp[2], temp[3]);
            }else{
                ClassrOGE classroge = classrFacade.findClassrWithProperties(temp[5], Integer.valueOf(temp[6]), temp[7]);
                if(classroge.getId()==-1){
                    //does not exist, hence create it
                    classroge = classrFacade.createClassrOGE(temp[5], Integer.valueOf(temp[6]), temp[7]);
                }else{
                    ClassingOGE classingoge = ejbFacade.
                            findClassingWithProperties(studentoge.getId(),classroge.getId(),temp[8]);
                    if(classingoge.getId()==-1){
                        //does not exist, hence create it
                        classingoge = ejbFacade.createClassingOGE(studentoge.getId(),classroge.getId(),temp[8]);
                    }                
                }
                
            }
        }
        }catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            //logger.debug(e.getLocalizedMessage());
        }
        return toreturn;
    }
    public ClassingOGE getSelected() {
        if (current == null) {
            current = new ClassingOGE();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ClassingOGEFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (ClassingOGE) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ClassingOGE();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClassingOGECreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ClassingOGE) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClassingOGEUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ClassingOGE) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ClassingOGEDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ClassingOGE getClassingOGE(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ClassingOGE.class)
    public static class ClassingOGEControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ClassingOGEController controller = (ClassingOGEController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "classingOGEController");
            return controller.getClassingOGE(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ClassingOGE) {
                ClassingOGE o = (ClassingOGE) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ClassingOGE.class.getName());
            }
        }

    }

}
