package com.epam.prihodko.finaltask.controller;

import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.logic.impl.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
    private Map<CommandName,ICommand> mapCommand = new HashMap<CommandName,ICommand>();
    public CommandHelper(){
        ICommand goToPageCommand = new GoToPageCommand();
        mapCommand.put(CommandName.LOGIN,new LoginCommand());
        mapCommand.put(CommandName.LOGOUT,new LogoutCommand());
        mapCommand.put(CommandName.SIGNUP,new SignUpCommand());
        mapCommand.put(CommandName.UPDATE,new UpdatePersonCommand());
        mapCommand.put(CommandName.GO_TO_REGISTR_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_APARTMENTTABLE_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_USER_PERSONAL_DATA_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_INF_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_MAKE_AN_ORDER_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_CHANGE_ORDER_PAGE,goToPageCommand);
        mapCommand.put(CommandName.PREVIOUS_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_CHANGE_APARTMENT_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_CREATE_APARTMENT_PAGE,goToPageCommand);
        mapCommand.put(CommandName.GO_TO_CHANGE_ORDER_PAGE,goToPageCommand);
        mapCommand.put(CommandName.MAKE_AN_ORDER,new MakeAnOrderCommand());
        mapCommand.put(CommandName.MAKE_AN_APARTMENT,new MakeAnApartmentCommand());
        mapCommand.put(CommandName.UPDATE_ORDER,new UpdateOrderCommand());
        mapCommand.put(CommandName.REMOVE_ORDER,new DeleteOrderCommand());
        mapCommand.put(CommandName.UPDATE_APARTMENT,new UpdateApartmentCommand());
        mapCommand.put(CommandName.REMOVE_APARTMENT,new DeleteApartmentCommand());
        mapCommand.put(CommandName.FIND_APARTMENT,new FindApartmentCommand());
        mapCommand.put(CommandName.TO_RESERVE_APARTMENT,new ToReserveApartmentCommand());
    }
    public ICommand defineCommand(HttpServletRequest request) {
        String nameCommand = request.getParameter(RequestParameterName.COMMAND_NAME).toUpperCase().replace("-","_");
        CommandName commandName = CommandName.valueOf(nameCommand);
        ICommand iCommand = null;
        switch (commandName){
            case LOGIN:
                iCommand = mapCommand.get(CommandName.LOGIN);
                break;
            case LOGOUT:
                iCommand = mapCommand.get(CommandName.LOGOUT);
                break;
            case SIGNUP:
                iCommand = mapCommand.get(CommandName.SIGNUP);
                break;
            case UPDATE:
                iCommand = mapCommand.get(CommandName.UPDATE);
                break;
            case GO_TO_REGISTR_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_REGISTR_PAGE);
                break;
            case GO_TO_APARTMENTTABLE_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_APARTMENTTABLE_PAGE);
                break;
            case GO_TO_USER_PERSONAL_DATA_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_USER_PERSONAL_DATA_PAGE);
                break;
            case GO_TO_MAKE_AN_ORDER_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_MAKE_AN_ORDER_PAGE);
                break;
            case GO_TO_CREATE_APARTMENT_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_CREATE_APARTMENT_PAGE);
                break;
            case MAKE_AN_ORDER:
                iCommand = mapCommand.get(CommandName.MAKE_AN_ORDER);
                break;
            case MAKE_AN_APARTMENT:
                iCommand = mapCommand.get(CommandName.MAKE_AN_APARTMENT);
                break;
            case PREVIOUS_PAGE:
                iCommand = mapCommand.get(CommandName.PREVIOUS_PAGE);
                break;
            case GO_TO_INF_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_INF_PAGE);
                break;
            case GO_TO_CHANGE_ORDER_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_CHANGE_ORDER_PAGE);
                break;
            case UPDATE_ORDER:
                iCommand = mapCommand.get(CommandName.UPDATE_ORDER);
                break;
            case REMOVE_ORDER:
                iCommand = mapCommand.get(CommandName.REMOVE_ORDER);
                break;
            case FIND_APARTMENT:
                iCommand = mapCommand.get(CommandName.FIND_APARTMENT);
                break;
            case TO_RESERVE_APARTMENT:
                iCommand = mapCommand.get(CommandName.TO_RESERVE_APARTMENT);
                break;
            case GO_TO_CHANGE_APARTMENT_PAGE:
                iCommand = mapCommand.get(CommandName.GO_TO_CHANGE_APARTMENT_PAGE);
                break;
            case UPDATE_APARTMENT:
                iCommand = mapCommand.get(CommandName.UPDATE_APARTMENT);
                break;
            case REMOVE_APARTMENT:
                iCommand = mapCommand.get(CommandName.REMOVE_APARTMENT);
                break;
        }
        return iCommand;
    }

}
