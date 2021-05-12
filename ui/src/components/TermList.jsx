import React from "react"
const TermList = (props) => {
    return (
        props.terms.map((val, idx) => {
            let term = `term-${idx}`;
            return (
                <tr key={val.index}>
                    <td>
                        <input type="text"  name="term" data-id={idx} id={term} className="form-control " />
                    </td>
                    <td>
                        {
                            idx===0?<button onClick={()=>props.add()} type="button" className="btn btn-primary text-center">
                                    <i className="fas fa-plus-circle"></i></button>
                                : <button className="btn btn-danger" onClick={(() => props.delete(val))} ><i className="fa fa-minus" aria-hidden="true"></i></button>
                        }

                    </td>
                </tr >
            )
        })
    )
}
export default TermList