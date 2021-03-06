import React from 'react';
import { Link } from 'react-router-dom'; 
import Persons from '../../models/member/persons';
import BaseService from '../../service/member/base.service';
import * as toastr from 'toastr';   
 
 
function Del(ID:number) {
    BaseService.delete("/member/delete/", ID).then(
        (rp) => {
            if (rp.Status) {
                toastr.success('Member saved.');    
                window.location.reload();
            } else { 
                toastr.error(rp.Messages);
                console.log("Messages: " + rp.Messages);
                console.log("Exception: " + rp.Exception);
            }
        }
    );
}
  
interface IProps {
    persons: Persons;  
}

const TableRow: React.FunctionComponent<IProps> = (props) => { 
    return (
        <tr>
            <td>
                {props.persons.customerId}
            </td>
            <td>
                {props.persons.firstName}
            </td>
            <td>
                {props.persons.lastName}
            </td>
            <td>
                {props.persons.email}
            </td>
            <td> 
                <Link to={"/edit/" + props.persons.customerId} className="btn btn-primary">수정</Link>
            </td>
            <td>
                <button onClick={()=>Del(Number(props.persons.customerId))} className="btn btn-danger">삭제</button>
            </td> 
        </tr>

    );
};
export default TableRow;