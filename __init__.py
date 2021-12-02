class CryptoError(Exception):
    """Raised when an operation attempts a state transition that's not
      allowed.

      Attributes:
          previous -- state at beginning of transition
          next -- attempted new state
          message -- explanation of why the specific transition is not allowed
      """

    def __init__(self, previous, next, message):
        self.previous = previous
        self.next = next
        self.message = 'Decryption unsuccessful'


# General variables:
cl = 40  # Column length.

chars = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
         'w', 'x', 'y', 'z', ' ', '-', ',', '.', ';', ':', '?', '!', '$', '°', 'º', 'ª', '/', '@', '_', '&', '%', '(',
         ')', 'á', 'à', 'â', 'ã', 'é', 'ê', 'í', 'ó', 'ô', 'õ', 'ú', 'ü', 'ñ', '0', '1', '2', '3', '4', '5', '6', '7',
         '8', '9']


def index_drawer():
    """
    Returns a list with values from 0 to 25 to encrypt messages according to the chars list above defined.
    """
    keyboard = len(chars)
    from random import randint
    first_index = randint(0, keyboard - 1)
    indexes_drawn = [first_index]
    while len(indexes_drawn) < keyboard:
        randomized_index = randint(0, keyboard - 1)
        if randomized_index not in indexes_drawn:
            indexes_drawn.append(randomized_index)
        else:
            pass
    return indexes_drawn


def cryptor(message):
    """
    By index_drawer function, encrypts a message.
    :param message:
    :type message: str
    :return: a list with the cryptographic key, the message lowercased and the crypted message according to the key drawed.
    """
    if type(message) is not str:
        raise TypeError
    msg = message.lower()
    ckey = index_drawer()

    normal_indexes = list()
    for letter in msg:
        normal_indexes.append(chars.index(letter))

    crypted_indexes = list()
    for value in normal_indexes:
        crypted_indexes.append(ckey[value])

    crypted_message = str()
    for cvalue in crypted_indexes:
        crypted_message += chars[cvalue]

    return ckey, crypted_message, msg


def decryptor(data):
    """
    Receives the needed data to decipher a encrypted message and do it.
    :param data: Data for decryption process (encrypted message and its key).
    :type data: list.
    :return: The message decrypted (string).
    """
    ckey = data[0]  # Cryptographic key.
    cmsg = data[1]  # Message encrypted.

    decrypted_message = str()
    index_oncrypt = list()
    index_decrypt = list()

    # Translating encrypted message characters (cmsg) in their index at normal list (chars):
    for letter in cmsg:
        index_oncrypt.append(chars.index(letter))

    # Detecting the above indexes inside the cryptographic key (ckey):
    for index in index_oncrypt:
        index_decrypt.append(ckey.index(index))

    # Translating normal indexes in the characters of the normal list (chars):
    for index in index_decrypt:
        decrypted_message += chars[index]

    return decrypted_message


def head():
    print('=' * cl)
    print('Cryptor Gheowin - Betha Version'.center(cl))
    print('Writen by Geovani L. Dias'.center(cl))
    print('-' * cl)


def main():
    head()
    print('Options:'.center(cl))
    print('|1: Encrypt a message')
    print('|2: Decrypt a message')
    print('|3: See the charaters table')
    print('-' * cl)
    q1 = 0
    while q1 not in ('1', '2', '3'):
        q1 = input('What do you want to do? Insert an option \naccording to the list above: ')

    q1 = int(q1)

    if q1 == 1:
        print('-' * cl)
        to_encrypt = str(input('Insert the message to encrypt: '))
        enigma = cryptor(to_encrypt)
        print(f'Your message:'.center(cl))
        print(to_encrypt)
        print(f'Your message encrypted:'.center(cl))
        print(enigma[1])
        print('Your key (copy/paste it carefully for future decryption):'.center(cl))
        for index, letter in enumerate(enigma[0]):
            print(letter, end='')
            if index == (len(chars) - 1):
                print('')
            else:
                print(',', end='')
        print('-' * cl)

    elif q1 == 2:
        print('-' * cl)
        to_decrypt = str(input('Insert the message to decrypt: '))
        strkey = str(input('Insert the cryptographic key: '))
        str_listkey = list(strkey.split(","))
        listkey = [int(str_index) for str_index in str_listkey]

        data = [listkey, to_decrypt]
        decryption = decryptor(data)

        print('-' * cl)
        print('Decrypting your message:')
        print(decryption)

    elif q1 == 3:
        print('-' * cl)
        print('Showing the characters table:'.center(cl))
        for index, letter in enumerate(chars):
            print(f'{index}: {letter}', end='')
            if index == (len(chars) - 1):
                print('.')
            else:
                if index > 0 and index / 6 == int(index / 6):
                    print('| ', end='\n')
                else:
                    print('| ', end='')


main()
