import * as React from 'react';
import Persons from '../../models/member/persons';
import { PersonsForm } from './personsForm';

interface IProps {
    persons: Persons;
    onChange: (fieldName: string, value: string) => void;
    onSave: () => void;
}

export const PersonsPage: React.FunctionComponent<IProps> = (props: IProps) => {  
    return (
        <PersonsForm
            persons={props.persons}
            onChange={props.onChange}
            onSave={props.onSave}
        />
    );
}
