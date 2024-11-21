### UI

1. Every UiState must be @Immutable data class
2. Every UiActions must be @Immutable interface
3. ViewModel can use only UseCases from domain
4. Every Ui component must have @Preview
5. If component have viewModel, There are must exist two overloads
    - With viewmodel
    - Without viewmodel

### Naming

1. Complex ui components:
    - Ui component = {Name} + {Ui type}
    - Viewmodel = {Name} + ViewModel
    - State = {Name} + UiState
    - Actions = {Name} + UiActions